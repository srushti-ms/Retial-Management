package com.example.retail_management.Controllers;


import com.example.retail_management.Dto.AuthRequest;
import com.example.retail_management.Dto.AuthResponse;
import com.example.retail_management.entity.ProductEntity;
import com.example.retail_management.entity.UserEntity;
import com.example.retail_management.services.ProductEntityService;
import com.example.retail_management.services.UserEntityServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntityControllers {

    @Autowired
    UserEntityServices userEntryServices;
    ProductEntityService productEntityService;

    @GetMapping("/")
    public ResponseEntity<?> getProducts(){
        List<ProductEntity> list =  productEntityService.getAllProducts();
        if(list.isEmpty()) {
            return new ResponseEntity<>("NO PRODUCTS FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUsers(@Valid @RequestBody UserEntity user) {
            if (userEntryServices.login(user))
            {
                return ResponseEntity.status(HttpStatus.OK).body("LOGGED IN");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NO ACCOUNT FOUND, PLEASE SIGN IN");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUsers(@Valid @RequestBody UserEntity user){
        if(userEntryServices.signin(user)){
            return ResponseEntity.status(HttpStatus.OK).body("signed in");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("email already exists, please login");

    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> users = userEntryServices.getAll();
        if(users.isEmpty()){
            return new ResponseEntity<>("no users found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        try {
            UserEntity user = userEntryServices.searchById(id);
            if(user == null){
                return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format");
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllUsers(){
        userEntryServices.deleteAll();
        return new ResponseEntity<>("all users deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        try {
            boolean deleted = userEntryServices.deleteById(id);
            if(deleted){
                return new ResponseEntity<>("user deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID format");
        }

    }
}
