package org.twspring.capstone1.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Category;
import org.twspring.capstone1.Model.Product;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Interfaces.IProductService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;

    //fixes the circular dependency
    @Lazy
    @Autowired
    private UserService userService;

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

    @Override
    public int applySaleToProductsByCategory(int userId, int categoryId, double salePercentage) {

        User user = userService.getUser(userId);

        if (categoryService.getCategory(categoryId) == null){
            return 1; //case 1: category not found
        }
        if (user == null){
            return 2;//case 2: Admin not found
        }
        if (!user.getRole().equalsIgnoreCase("Admin")){
            return 3; //case 3: Only admins can apply a discount
        }
        if (salePercentage<10||salePercentage>90){
            return 4; //case 4: Sale must be between 10 and 90 percent
        }
        salePercentage = salePercentage/100.0;
        for (Product product : products) {
            if (product.getCategoryId() == categoryId){
                if (product.isOnSale()){
                    return 5; //category already on sale
                }
                product.setPrice(product.getPrice()-(product.getPrice()*salePercentage));
                product.setOnSale(true);
            }
        }
        return 0; //success
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
