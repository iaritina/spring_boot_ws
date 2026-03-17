package mg.tpws.restapi.service;

import mg.tpws.restapi.dto.ProductDTO;
import mg.tpws.restapi.model.Product;
import mg.tpws.restapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById(Long id){
        return repository.findById(id).orElseThrow();
    }


    public Product save(ProductDTO dto){
        Product p = new Product(dto.getName(),dto.getPrice());
        return repository.save(p);
    }

    public boolean deleteById(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }


}
