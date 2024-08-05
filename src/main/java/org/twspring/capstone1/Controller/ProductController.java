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

    //EXTRA: GET PRODUCTS BY CATEGORY
    @GetMapping("get/product/by_category/{category}")
    public ResponseEntity getProductByCategory(@PathVariable String category) {
        if (productService.getProductsByCategory(category).isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(productService.getProductsByCategory(category));
    }
    //=======================================POST=======================================
    @PostMapping("/add/product")
    public ResponseEntity addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (productService.addProduct(product)) {
            return ResponseEntity.status(201).body("Product added successfully");
        }
        return ResponseEntity.status(409).body(new ApiResponse("Category with ID "+product.getCategoryId()+" does not exist"));
    }
    //FOR TESTS
    @PostMapping("/add/products")
    public ResponseEntity addProducts() {
        Product product1 = new Product(1,"face cream",20.50,3);
        Product product2 = new Product(2,"eye cream",15.50,3);
        Product product3 = new Product(3,"key board",35.70,2);
        Product product4 = new Product(4,"head phones",19.20,2);
        Product product5 = new Product(5,"white shirt",10.00,1);
        Product product6 = new Product(6,"blue jeans",15.50,1);
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);
        productService.addProduct(product5);
        productService.addProduct(product6);
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
