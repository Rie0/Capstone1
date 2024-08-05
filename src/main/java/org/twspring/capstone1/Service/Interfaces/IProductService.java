package org.twspring.capstone1.Service.Interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import org.twspring.capstone1.Model.Product;

import java.util.ArrayList;

public interface IProductService {
    public ArrayList<Product> getProducts();
    public Product getProduct(int id);
    public ArrayList<Product> getProductsByCategory(String category);
    public boolean addProduct(Product product);
    public boolean updateProduct(int id, Product product);
    public int applySaleToProductsByCategory(int userId, int categoryId, double salePercentage);
    public boolean deleteProduct(int id);
}
