package com.example.retail_management.ManagementRepository;

import com.example.retail_management.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductManagementRepo extends MongoRepository<ProductEntity,String> {
}
