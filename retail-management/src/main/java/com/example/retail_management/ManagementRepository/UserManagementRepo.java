package com.example.retail_management.ManagementRepository;

import com.example.retail_management.entity.UserEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserManagementRepo extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByusername(String username);
}
