package com.example.weather.util;

import com.example.weather.exception.APIException;
import com.example.weather.exception.WeatherPredictionExceptionEnum;
import com.example.weather.model.StandardError;

public class WeatherAPIExceptionUtil {
    public static void throwException(WeatherPredictionExceptionEnum exceptionEnum, String exceptionMessage){
        StandardError error = new StandardError(
                exceptionEnum.getFailureCode(),
                exceptionEnum.getFailureDescription()
        );
        throw new APIException(exceptionMessage,exceptionEnum.getHttpStatusCode(),error);
    }

}
