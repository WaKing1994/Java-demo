package com.example.demo.manager.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.manager.UserMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMngImpl implements UserMng {

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User get(Integer id) {
        return userDao.get(id);
    }

    @Override
    public void update(Integer id, String username, String password) {
        userDao.update(id, username, password);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public void add(String username, String password) {
        userDao.add(username, password);
    }

    @Autowired
    private UserDao userDao;
}
