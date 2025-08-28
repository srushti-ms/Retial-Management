package com.example.retail_management.Controllers;


import com.example.retail_management.Dto.AuthRequest;
import com.example.retail_management.Dto.AuthResponse;
import com.example.retail_management.entity.UserEntity;
import com.example.retail_management.services.UserEntityServices;
import com.example.retail_management.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntityControllers {

    @Autowired
    UserEntityServices userEntryServices;

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginUsers(@Valid @RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token));

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUsers(@Valid @RequestBody UserEntity user){
        boolean added = userEntryServices.signin(user);
        if(added){
            return new ResponseEntity<>("user successfully signed in", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("username "+ user.getUsername() +" already exists", HttpStatus.CONFLICT);
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
