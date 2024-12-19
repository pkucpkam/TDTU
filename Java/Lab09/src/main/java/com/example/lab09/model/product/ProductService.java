package com.example.lab09.model.product;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAll(){
        return repository.findAll();
    }

    public Product add(Product product){
        return repository.save(product);
    }

    public Product findById(String id){
        return repository.findById(id).orElse(null);
    }

    public boolean isExist(String id){
        return repository.existsById(id);
    }

    public String getNameById(String id){
        Optional<Product> product = repository.findById(id);
        return product.map(Product::getName).orElse(null);
    }

    public boolean delete(String id){
        try {
            repository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean update(String id, Product product){
        Optional<Product> product1 = repository.findById(id);
        if(!product1.isPresent()) return false;

        Product productUpdate = product1.get();
        productUpdate.setName(product.getName());
        productUpdate.setIllustration(product.getIllustration());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setDescription(product.getDescription());

        repository.save(productUpdate);
        return true;
    }

    public Product updatePatch(String id, Product request) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (request.getName() != null) {
                product.setName(request.getName());
            }
            if (request.getDescription() != null) {
                product.setDescription(request.getDescription());
            }
            if (request.getIllustration() != null) {
                product.setIllustration(request.getIllustration());
            }
            if (request.getPrice() != 0) {
                product.setPrice(request.getPrice());
            }
            return repository.save(product);
        }
        return null;
    }
}
