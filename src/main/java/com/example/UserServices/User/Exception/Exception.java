package com.example.UserServices.User.Exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class Exception {

    private String code;
    private String message;


    public Exception(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
