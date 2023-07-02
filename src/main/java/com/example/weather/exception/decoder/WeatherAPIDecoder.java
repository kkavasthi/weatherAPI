package com.example.weather.exception.decoder;

import com.example.weather.exception.APIException;

import com.example.weather.exception.WeatherPredictionExceptionEnum;
import com.example.weather.model.OpenAPIError;

import com.example.weather.model.StandardError;
import com.example.weather.util.WeatherAPIExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.example.weather.exception.WeatherPredictionExceptionEnum.*;


@Component
public class WeatherAPIDecoder implements ErrorDecoder {

    private static final String EXCEPTION_CALLING_OPEN_WEATHER_API = "Error in Calling Exception API with status %s and reason %s";
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherAPIDecoder.class);

    public  static final StandardError internalServerError;

    static {
        internalServerError=new StandardError(
                OPENAPI_INTERNAL_SERVER_ERROR.getFailureCode(),
                OPENAPI_INTERNAL_SERVER_ERROR.getFailureDescription()
        );
    }
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        Exception exception = defaultErrorDecoder.decode(methodKey,response);

        if(exception instanceof RetryableException){
            return exception;
        }

        String exceptionMessage = String.format(EXCEPTION_CALLING_OPEN_WEATHER_API, response.status(), response.reason());

        LOGGER.error(exceptionMessage);

        APIException internalServerErrorException = new APIException(exceptionMessage,OPENAPI_INTERNAL_SERVER_ERROR.getHttpStatusCode(),internalServerError);

        try{
            String body = response.body().toString();
            Map<String,String> objectMap = new ObjectMapper().readValue(body,Map.class);

            if(objectMap.containsKey("message")){
                StandardError standardError = new StandardError(objectMap.get("cod"),objectMap.get("message"));
                return new APIException(exceptionMessage, HttpStatus.resolve(response.status()),standardError);
            }

        } catch (JsonProcessingException e){

        }

        switch (response.status()){
            case 400:
                WeatherAPIExceptionUtil.throwException(OPENAPI_BAD_REQUEST,"Required fields are null");
                break;
            case 404:
                WeatherAPIExceptionUtil.throwException(OPENAPI_RECORD_NOT_FOUND,"Resource not found");
                break;
            default:
                return internalServerErrorException;
        }
        return internalServerErrorException;


    }


}
