package com.example.retail_management.services;

import com.example.retail_management.ManagementRepository.UserManagementRepo;
import com.example.retail_management.entity.UserEntity;
import com.example.retail_management.entity.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceClass implements UserDetailsService {

    @Autowired
    UserManagementRepo userManagementRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<UserEntity> user = userManagementRepo.findByusername(username);
        if(user.isPresent()){
            UserEntity u = user.get();
            return new UserPrincipal(u);
        }
        else{
            throw new UsernameNotFoundException("email not registered: " + username);
        }
    }


}
