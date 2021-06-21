package com.example.UserServices.User.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

//handles global exceptions

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception){
        Error errorDetails =
                new Error( HttpStatus.NOT_FOUND.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    // 400
    @ExceptionHandler(ServerError.class)
    public ResponseEntity<?> severErrorHTTP(ServerError exception){
        Error errorDetails =
                new Error(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    // handling global exception 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, HttpStatus status){
        Error errorDetails =
                new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
