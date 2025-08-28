package com.example.retail_management.entity;


import com.example.retail_management.util.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

import org.springframework.data.annotation.Id;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    private Role role;
    @DBRef
    private List<ProductEntity> Products = new ArrayList<>();
}
