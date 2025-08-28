package com.example.retail_management.services;

import com.example.retail_management.entity.ProductEntity;
import com.example.retail_management.ManagementRepository.ProductManagementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


import java.util.List;
@Service
public class ProductEntityService {
    @Autowired
    private ProductManagementRepo productManagementRepo;

    public void addProduct(ProductEntity product){
        productManagementRepo.save(product);
    }

    public ProductEntity searchById(String id){
        Optional<ProductEntity> product = productManagementRepo.findById(id);
        return product.orElse(null); // or throw custom exception
    }

    public List<ProductEntity> getAllProducts(){
        return productManagementRepo.findAll();
    }

    public boolean deleteById(String id) {
        if (productManagementRepo.existsById(id)) {
            productManagementRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllProducts(){
        productManagementRepo.deleteAll();
    }
}
