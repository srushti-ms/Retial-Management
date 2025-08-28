package com.example.retail_management.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
public class ProductEntity {
    @Id
    private String id;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private Double price;
}
