package com.school.project.authservice.advisors;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.libs.common.responses.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value ={ CustomCheckedException.class })
    protected ResponseEntity<?> handleCheckedExceptions(CustomCheckedException ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse resp = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, resp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value ={ Exception.class })
    protected ResponseEntity<?> handleGeneralExceptions(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse resp = new ErrorResponse("An internal server error occurred: "+ ex.getMessage());
        return handleExceptionInternal(ex, resp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value ={ RuntimeException.class })
    protected ResponseEntity<?> handleRuntimeExceptions(RuntimeException ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse resp = new ErrorResponse("An internal server error occurred: "+ ex.getMessage());
        return handleExceptionInternal(ex, resp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value ={ UsernameNotFoundException.class })
    protected ResponseEntity<?> handleUsernameNotFoundExceptions(UsernameNotFoundException ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse resp = new ErrorResponse("An internal server error occurred: "+ ex.getMessage());
        return handleExceptionInternal(ex, resp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ex.printStackTrace();
        ErrorResponse resp = new ErrorResponse(ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
