package com.example.UserServices.User.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED) //401
public class UnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
