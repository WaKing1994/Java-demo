package com.example.demo.action.Request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.example.demo.common.WebErrors;

@ApiModel
public class WebRequestForCert {
    @ApiModelProperty(
            value = "授权编码",
            required = true
    )
    protected String token;


    public WebErrors createWebErrors() {

        WebErrors errors = new WebErrors();


        return errors;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
