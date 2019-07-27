package com.example.demo.common;

import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unchecked")
public class WebErrors {

    /**
     * 默认错误信息属性名称
     */
    public static final String ERROR_ATTR_NAME = "errors";
    public static final String ERROR_CODE_NAME = "error_code";

    private String success;
    private String error_code;
    private String message;


    public Boolean hasErrors() {
        return success != "0";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public WebErrors() {
        this.setSuccess("1");
        this.setError_code("success");
    }

    public static String getErrorAttrName() {
        return ERROR_ATTR_NAME;
    }

    public static String getErrorCodeName() {
        return ERROR_CODE_NAME;
    }
}
