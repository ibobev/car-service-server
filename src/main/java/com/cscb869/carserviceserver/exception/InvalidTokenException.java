package com.cscb869.carserviceserver.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends  RuntimeException{
    private HttpStatus status;
    private String message;

    public InvalidTokenException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
