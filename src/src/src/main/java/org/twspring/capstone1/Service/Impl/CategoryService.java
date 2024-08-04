package org.twspring.capstone1.Service.Impl;

import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Category;
import org.twspring.capstone1.Service.Interfaces.ICategoryService;

import java.util.ArrayList;

@Service
public class CategoryService implements ICategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    @Override
    public ArrayList<Category> getCategories() {
        return categories;
    }

    @Override
    public Category getCategory(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public boolean updateCategory(int id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteCategory(int id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
