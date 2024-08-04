package org.twspring.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.twspring.capstone1.Api.ApiResponse;
import org.twspring.capstone1.Model.Product;
import org.twspring.capstone1.Service.Impl.ProductService;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //=======================================GET=======================================
    @GetMapping("/get/products")
    public ResponseEntity getProducts() {
        if (productService.getProducts().isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(productService.getProducts());
    }
    @GetMapping("/get/product/{id}")
    public ResponseEntity getProduct(@PathVariable int id) {
        if (productService.getProduct(id)==null){
            return ResponseEntity.status(404).body(new ApiResponse("No product with ID "+id+" found"));
        }
        return ResponseEntity.status(200).body(productService.getProduct(id));
    }
    //=======================================POST=======================================
    @PostMapping("/add/product")
    public ResponseEntity addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        productService.addProduct(product);
        return ResponseEntity.status(201).body("Product added successfully");
    }
    //=======================================UPDATE=======================================
    @PutMapping("/update/product/{id}")
    public ResponseEntity updateProduct(@PathVariable int id,@Valid @RequestBody Product product,Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean isUpdated = productService.updateProduct(id,product);
        if (isUpdated) {
            return ResponseEntity.status(201).body("Product updated successfully");
        }
        return ResponseEntity.status(404).body(new ApiResponse("No product with ID "+id+" found"));
    }
    //=======================================DELETE=======================================
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.status(201).body("Product deleted successfully");
        }
        return ResponseEntity.status(404).body(new ApiResponse("No product with ID "+id+" found"));
    }
}
