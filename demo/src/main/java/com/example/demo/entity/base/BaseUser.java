package com.example.demo.entity.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseUser {
    private Integer id;
    private String username;
    private String password;

    public BaseUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BaseUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
