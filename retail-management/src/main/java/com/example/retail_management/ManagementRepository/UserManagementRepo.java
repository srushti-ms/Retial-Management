package com.example.retail_management.ManagementRepository;

import com.example.retail_management.entity.UserEntity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserManagementRepo extends MongoRepository<UserEntity, String> {
    boolean existsByusername(String username);
}
