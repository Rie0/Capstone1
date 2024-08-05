package org.twspring.capstone1.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Category;
import org.twspring.capstone1.Model.Product;
import org.twspring.capstone1.Service.Interfaces.IProductService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product getProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Product> getProductsByCategory(String category) {
        ArrayList<Product> foundProducts = new ArrayList<>();
        //search for a category
        for (Category cat: categoryService.getCategories()){
            if (cat.getName().equalsIgnoreCase(category)){
                //search for products with the category
                for (Product product : products) {
                    if (product.getCategoryId() == cat.getId()){
                        foundProducts.add(product);
                    }
                }

            }
        }
        return foundProducts;
    }

    //Add new Product
    @Override
    public boolean addProduct(Product product) {
        //check if category exists
        if (categoryService.getCategory(product.getCategoryId()) == null){
            return false;
        }
        products.add(product);
        return true;
    }

    //Update an existing product
    @Override
    public boolean updateProduct(int id, Product product) {
        //reviews aren't updating with the product details
        int numberOfReviews = getProduct(id).getNumberOfReview();
        double averageScore = getProduct(id).getAverageScore();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                product.setNumberOfReview(numberOfReviews);
                product.setAverageScore(averageScore);
                return true;
            }
        }
        return false;
    }

    //Delete a product
    @Override
    public boolean deleteProduct(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
