package com.example.retail_management.services;

import com.example.retail_management.ManagementRepository.UserManagementRepo;
import com.example.retail_management.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntityServices {

    @Autowired
    UserManagementRepo userManagementRepo;

    public boolean login(UserEntity user){
        if(!userManagementRepo.existsByusername(user.getUsername())) {
            return true;
        }
        return false;
    }

    public boolean signin(UserEntity user){
        if(!userManagementRepo.existsByusername(user.getUsername())) {
            userManagementRepo.save(user);
            return true;
        }
        return false;
    }


    public List<UserEntity> getAll(){
        return userManagementRepo.findAll();
    }

    public UserEntity searchById(String id){
        Optional<UserEntity> user = userManagementRepo.findById(id);
        return user.orElse(null);
    }

    public void deleteAll(){
        userManagementRepo.deleteAll();
    }

    public boolean deleteById(String id){
        if (userManagementRepo.existsById(id)) {
            userManagementRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
