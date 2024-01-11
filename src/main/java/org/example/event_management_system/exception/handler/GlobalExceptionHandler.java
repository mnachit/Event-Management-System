package org.example.event_management_system.exception.handler;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.util.ErrorMessage;
import org.example.event_management_system.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response<String>> handelIllegalArgumentException(Exception e)
    {
        Response<String> response = new Response<>();
        response.setErrors(((ValidationException) e).getErrorMessages());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handelMethodArgumentNotValidException(MethodArgumentNotValidException ev){
        Response<Map<String, String>> response = new Response<>();
        Map<String, String> setFieldsValidation = new HashMap<>();
        ev.getBindingResult().getFieldErrors().forEach(
                e -> {
                    setFieldsValidation.put(e.getField(), e.getDefaultMessage());
                }
        );
        response.setErrorMap(setFieldsValidation);
        return ResponseEntity.badRequest().body(response);
    }

}