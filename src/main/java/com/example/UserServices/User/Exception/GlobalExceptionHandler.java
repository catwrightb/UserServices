package com.example.UserServices.User.Exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;


//handles global exceptions

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ResponseBody
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception){
        Exception errorDetails =
                new Exception( HttpStatus.NOT_FOUND.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }




    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> severErrorHTTP(BadRequestException exception){
        Exception errorDetails =
                new Exception(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // 409
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> resourceAlreadyExistHandler(ResourceAlreadyExistException exception){
        Exception errorDetails =
                new Exception(HttpStatus.CONFLICT.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }


    // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException exception){
        Exception errorDetails =
                new Exception(HttpStatus.UNAUTHORIZED.toString(),  exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(java.lang.Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> globalExceptionHandling(java.lang.Exception exception){
        Exception errorDetails =
                new Exception(HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
