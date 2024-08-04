package org.twspring.capstone1.Service.Impl;

import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Product;
import org.twspring.capstone1.Service.Interfaces.IProductService;

import java.util.ArrayList;

@Service
public class ProductService implements IProductService {
    ArrayList<Product> products = new ArrayList<Product>();

    //Get All Products
    public ArrayList<Product> getProducts() {
        return products;
    }
    //Get One Products
    public Product getProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    //Add new Product
    @Override
    public void addProduct(Product product) {
        products.add(product);

    }

    //Update an existing product
    @Override
    public boolean updateProduct(int id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
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
