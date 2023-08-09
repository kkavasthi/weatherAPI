package com.example.weather.util;

import com.example.weather.exception.OpenAPIException;
import com.example.weather.exception.WeatherPredictionExceptionEnum;
import com.example.weather.model.StandardError;

public class WeatherAPIExceptionUtil {
    public static void throwException(WeatherPredictionExceptionEnum exceptionEnum, String exceptionMessage){
        StandardError error = new StandardError(
                exceptionEnum.getFailureCode(),
                exceptionEnum.getFailureDescription()
        );
        throw new OpenAPIException(exceptionMessage,exceptionEnum.getHttpStatusCode(),error);
    }

}
