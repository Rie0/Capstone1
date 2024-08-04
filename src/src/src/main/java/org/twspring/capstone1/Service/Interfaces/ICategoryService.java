package org.twspring.capstone1.Service.Interfaces;

import org.twspring.capstone1.Model.Category;

import java.util.ArrayList;

public interface ICategoryService {

    public ArrayList<Category> getCategories();
    public Category getCategory(int id);
    public void addCategory(Category category);
    public boolean updateCategory(int id, Category category);
    public boolean deleteCategory(int id);
}
