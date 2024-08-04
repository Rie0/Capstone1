package org.twspring.capstone1.Service.Interfaces;

import org.twspring.capstone1.Model.Product;

import java.util.ArrayList;

public interface IProductService {
    public ArrayList<Product> getProducts();
    public Product getProduct(int id);
    public void addProduct(Product product);
    public boolean updateProduct(int id, Product product);
    public boolean deleteProduct(int id);
}
