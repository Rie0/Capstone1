package org.twspring.capstone1.Service.Interfaces;

import org.twspring.capstone1.Model.User;

import java.util.ArrayList;

public interface IUserService {
    public ArrayList<User> getUsers();
    public User getUser(int id);
    public void addUser(User user);
    public boolean updateUser(int id, User user);
    public boolean deleteUser(int id);
}
