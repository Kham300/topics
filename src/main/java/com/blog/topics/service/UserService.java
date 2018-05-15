package com.blog.topics.service;

import com.blog.topics.model.User;
import com.blog.topics.to.UserTo;
import com.blog.topics.util.errors.NotFoundException;

import java.util.List;

public interface UserService {
    
    User getById(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    User save(User user);
    
    void delete (int id) throws NotFoundException;
    
    List<User> getAll();

    public User findByUsername(String username);

    void update(User user);

    void update(UserTo user);
}
