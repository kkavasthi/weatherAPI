package com.example.weather.exception;

import com.example.weather.model.StandardError;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Data
public class WeatherAPIException extends RuntimeException {
    @Getter
    private HttpStatus httpStatus;
    private StandardError error;

    public WeatherAPIException(String message, HttpStatus httpStatus, StandardError error){
        super(message);
        this.httpStatus=httpStatus;
        this.error=error;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public StandardError getError() {
        return this.error;
    }
}
