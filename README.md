# Utilisation des fichiers Postman

Ce projet fournit trois fichiers Postman pour tester l'API :

- `postman.local_collection.json` : collection pour l'environnement local
- `postman.prod_collection.json` : collection pour l'environnement de production
- `postman.environment.json` : environnement partagé contenant les variables d'URL

## Import dans Postman

1. Ouvrir Postman.
2. Cliquer sur `Import`.
3. Importer les fichiers suivants :
   - `postman.local_collection.json`
   - `postman.prod_collection.json`
   - `postman.environment.json`
4. Vérifier que l'environnement `tpws-spring` apparaît dans Postman.

## Variables d'environnement

Le fichier `postman.environment.json` contient les variables suivantes :

- `baseUrl = http://localhost:8080`
- `baseUrlProd = https://spring-boot-ws.onrender.com`

Les collections utilisent ces variables :

- la collection locale `Spring Boot Local` utilise `{{baseUrl}}`
- la collection production `Spring Boot Online` utilise `{{baseUrlProd}}`

## Contenu des collections

Les deux collections couvrent les mêmes groupes de requêtes :

- `Login`
- `Admins > Users`
- `Admins > Categories`
- `Tickets`
- `Ticket Comments`
- `Tickets assignments`
- `Stats`

Exemples d'endpoints inclus :

- authentification : `POST /api/auth/login`
- utilisateurs : `GET /api/users`, `GET /api/users/me`, `PUT /api/users/{id}`, `DELETE /api/users/{id}`
- categories : `GET /api/categories`, `GET /api/categories/{id}`, `POST /api/categories`, `PUT /api/categories/{id}`, `DELETE /api/categories/{id}`
- tickets : `GET /api/tickets`, `GET /api/tickets/me`, `GET /api/tickets/me/agent/open`, `GET /api/tickets/{id}`, `POST /api/tickets`, `PATCH /api/tickets/{id}/close`, `DELETE /api/tickets/{id}`
- commentaires : `POST /api/tickets/{ticketId}/comments`, `GET /api/tickets/{ticketId}/comments`, `DELETE /api/tickets/{ticketId}/comments/{commentId}`
- affectations : `POST /api/tickets/{ticketId}/assignments/agent/{agentId}`, `GET /api/tickets/{ticketId}/assignments`
- statistiques : `GET /api/tickets/stats/by-category`, `GET /api/tickets/stats/by-status`

## Utiliser la collection locale

1. Démarrer l'application en local sur `http://localhost:8080`.
2. Dans Postman, sélectionner l'environnement `tpws-spring`.
3. Ouvrir la collection `Spring Boot Local`.
4. Exécuter la requête `Login`.
5. Utiliser ensuite les autres requêtes selon le rôle nécessaire.

## Utiliser la collection de production

1. Dans Postman, sélectionner l'environnement `tpws-spring`.
2. Ouvrir la collection `Spring Boot Online`.
3. Exécuter la requête `Login`.
4. Utiliser ensuite les autres requêtes sur l'instance de production.

## Authentification

Certaines requêtes contiennent déjà un token Bearer d'exemple pour faciliter les tests. Si le token n'est plus valide :

1. lancer la requête `Login`
2. copier le token retourné par l'API
3. remplacer le token Bearer dans la requête à exécuter

Les exemples présents dans les collections utilisent différents rôles selon les endpoints testés :

- `ROLE_ADMIN`
- `ROLE_USER`
- `ROLE_AGENT`
