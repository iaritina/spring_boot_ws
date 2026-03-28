package mg.tpws.restapi.config;

import mg.tpws.restapi.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    JwtAuthenticationFilter jwtFilter;

    private final CustomUserDetailService customUserDetailsService;

    public SecurityConfig(CustomUserDetailService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        .requestMatchers("/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tickets/*/comments").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/tickets/*/comments")
                        .hasAnyRole("AGENT", "ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/api/tickets/me").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/tickets/**").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/**").hasAnyRole("AGENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tickets").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/api/tickets/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/assignments/**")
                        .hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/assignments/agent/**")
                        .hasAnyRole("ADMIN", "AGENT")

                        .requestMatchers(HttpMethod.GET, "/api/assignments/ticket/**")
                        .hasAnyRole("ADMIN", "AGENT")

                        
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

}
