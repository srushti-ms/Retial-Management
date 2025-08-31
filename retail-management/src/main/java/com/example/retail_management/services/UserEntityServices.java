package com.example.retail_management.services;

import com.example.retail_management.ManagementRepository.UserManagementRepo;
import com.example.retail_management.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntityServices {

    @Autowired
    UserManagementRepo userManagementRepo;

    public boolean login(UserEntity user){
        Optional<UserEntity> Optuser = userManagementRepo.findByusername(user.getUsername());

        if(Optuser.isPresent()){
            UserEntity u = Optuser.get();
            return user.getPassword().equals(u.getPassword());
        }
        return false;
    }

    public boolean signin(UserEntity user){
        Optional<UserEntity> Optuser = userManagementRepo.findByusername(user.getUsername());

        if(Optuser.isPresent()){
            return false;
        }
        userManagementRepo.save(user);
        return true;
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
