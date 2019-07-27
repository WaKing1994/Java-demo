package com.example.demo.action.Response;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class WebResponse implements Serializable {


    public static final String SUCCESS = "1";
    @ApiModelProperty(
            value = "返回码",
            required = true
    )
    private String error_code = "1";
    @ApiModelProperty(
            value = "错误消息",
            required = true
    )
    private String message = "success";
    public static final String DEFAULT_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public WebResponse() {
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
