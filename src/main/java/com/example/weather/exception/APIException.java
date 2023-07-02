package com.example.weather.exception;

import com.example.weather.model.StandardError;
import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {
    private HttpStatus httpStatusCode;
    private StandardError error;


    public APIException(String exceptionMessage, HttpStatus httpStatusCode, StandardError error) {
        super(exceptionMessage);
        this.httpStatusCode=httpStatusCode;
        this.error=error;
    }

    public HttpStatus getHttpStatusCode(){
        return httpStatusCode;
    }

    public StandardError getError(){
        return error;
    }
}
