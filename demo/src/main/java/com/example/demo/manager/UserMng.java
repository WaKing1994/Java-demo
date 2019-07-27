package com.example.demo.manager;

import com.example.demo.entity.User;

import java.util.List;

public interface UserMng {
    public List<User> findAll();

    public User get(Integer id);

    public void update(Integer id, String username, String password);

    public void delete(Integer id);

    public void add(String username, String password);
}
