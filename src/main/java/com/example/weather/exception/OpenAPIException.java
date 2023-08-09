package com.example.weather.exception;

import com.example.weather.model.StandardError;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class OpenAPIException extends RuntimeException {
    private HttpStatus httpStatusCode;
    private StandardError error;


    public OpenAPIException(String exceptionMessage, HttpStatus httpStatusCode, StandardError error) {
        super(exceptionMessage);
        this.httpStatusCode=httpStatusCode;
        this.error=error;
    }
}
