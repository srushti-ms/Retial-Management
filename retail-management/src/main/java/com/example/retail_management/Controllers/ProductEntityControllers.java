package com.example.retail_management.Controllers;


import com.example.retail_management.entity.ProductEntity;
import com.example.retail_management.services.ProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductEntityControllers {

    @Autowired
    ProductEntityService service;

    @PostMapping("/addProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductEntity product) {
        service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getProducts(){
        List<ProductEntity> list =  service.getAllProducts();
        if(list.isEmpty()) {
            return new ResponseEntity<>("NO PRODUCTS FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> searchProduct(@PathVariable String id){
        ProductEntity product = service.searchById(id);
        if(product != null){
            return new ResponseEntity<>(product , HttpStatus.OK);
        }
        return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(){
        service.deleteAllProducts();
        return new ResponseEntity<>("All products deleted sucessfully", HttpStatus.OK);
    }

}
