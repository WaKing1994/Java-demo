package com.example.demo.entity;

import com.example.demo.entity.base.BaseUser;

import javax.persistence.Entity;

@Entity
public class User extends BaseUser {
    public User(String username, String password) {
        super(username, password);
    }

    public User() {
        super();
    }

    public void init() {

    }
}
