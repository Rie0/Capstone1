package org.twspring.capstone1.Service.Impl;

import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Interfaces.IUserService;

import java.util.ArrayList;

@Service
public class UserService implements IUserService {
    ArrayList<User> users = new ArrayList<>();

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public boolean updateUser(int id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }
}
