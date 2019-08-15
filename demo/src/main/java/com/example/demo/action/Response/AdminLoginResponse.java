package com.example.demo.action.Response;

import com.example.demo.common.api.Mapping;
import io.swagger.annotations.ApiModelProperty;

public class AdminLoginResponse extends WebResponse {

    @ApiModelProperty(value = "用户姓名", required = true)
    @Mapping(value = "#root['user'].username")
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    @Mapping(value = "#root['user'].password")
    private String password;

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
