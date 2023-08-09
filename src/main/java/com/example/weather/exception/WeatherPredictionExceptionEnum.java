package com.example.weather.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public enum WeatherPredictionExceptionEnum  {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST,9001,"Bad-Request - Invalid parameters"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 9002,"Internal server error"),

    SERVER_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,9003,"Server Unavailable"),

    SERVER_TIME_OUT(HttpStatus.SERVICE_UNAVAILABLE,9004,"Service Timeout"),

    AUTHORIZATION_FAILURE(HttpStatus.UNAUTHORIZED,9005,"Invalid API KEY"),

    OPENAPI_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,9006,"OPEN API Internal Server Error"),

    OPENAPI_BAD_REQUEST(HttpStatus.BAD_REQUEST,9009,"city not found"),

    OPENAPI_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND,9008,"Resource not available");


    private HttpStatus httpStatusCode;

    private int failureCode;

    private String failureDescription;

    WeatherPredictionExceptionEnum(HttpStatus pHttpStatus, int pFailureCode, String pFailureDescription) {
        this.setHttpStatus(pHttpStatus);
        this.setFailureCode(pFailureCode);
        this.setFailureDescription(pFailureDescription);

    }

    public void setFailureDescription(String failureDescription) {
        this.failureDescription = failureDescription;
    }

    public void setFailureCode(int failureCode) {
        this.failureCode = failureCode;
    }

    public void setHttpStatus(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getFailureDescription(){
        return failureDescription;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getFailureCode() {
        return String.valueOf(failureCode);
    }
}
