package com.ghtk.social_network.exception.handler;

import com.ghtk.social_network.application.response.RestResponse;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = {
            Exception.class,
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            IdInvalidException.class,
            ValidationException.class
    })
    public ResponseEntity<RestResponse<Object>> handleException(Exception ex) {
        RestResponse<Object> er = new RestResponse<Object>();
        er.setStatusCode(HttpStatus.BAD_REQUEST.value());
        er.setMessage("Error occur");
        er.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        RestResponse<Object> er = new RestResponse<Object>();
        List<String> messages = new ArrayList<String>();
        er.setStatusCode(HttpStatus.BAD_REQUEST.value());
        er.setError("MethodArgumentNotValidException");
        for (FieldError error : errors) {
            messages.add(error.getDefaultMessage());
        }
        er.setMessage(messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse<Object>> checkAll(Exception ex){
        RestResponse<Object> er = new RestResponse<Object>();
        er.setStatusCode(HttpStatus.BAD_REQUEST.value());
        er.setMessage(ex.getMessage());
        er.setError("Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

}