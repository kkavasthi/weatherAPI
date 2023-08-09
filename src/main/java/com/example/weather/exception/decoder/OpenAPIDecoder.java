package com.example.weather.exception.decoder;

import com.example.weather.exception.OpenAPIException;

import com.example.weather.model.StandardError;
import com.example.weather.model.StandardErrorList;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.example.weather.exception.WeatherPredictionExceptionEnum.*;


@Component
public class OpenAPIDecoder implements ErrorDecoder {

    private static final String EXCEPTION_CALLING_OPEN_WEATHER_API = "Error in Calling Open API";
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAPIDecoder.class);

    private static final String PARSING_ERROR = "Error While Decoding the Error Response from Open API";

    private static final String PARSING_ERROR_BY_CODE = "Error while Decoding the Error Response from Open API and is caused by {}";

    private static OpenAPIException parsingException;
    public  static final StandardError internalServerError;

    static {
        internalServerError=new StandardError();
        internalServerError.setErrorCode(INTERNAL_SERVER_ERROR.getFailureCode());
        internalServerError.setErrorDescription(INTERNAL_SERVER_ERROR.getFailureDescription());
        parsingException = new OpenAPIException(PARSING_ERROR,INTERNAL_SERVER_ERROR.getHttpStatusCode(),internalServerError);
    }

    @Override
    public Exception decode(String methodKey, Response response) {

        String result;

        ObjectMapper jsonErrorMessageConvertor = new ObjectMapper();
        StandardErrorList errorList = new StandardErrorList();
        HttpStatus httpStatus = HttpStatus.resolve(response.status());
        LOGGER.error("Error while calling the Open API and Http Status is{}", response.status());
        try {
            result = response.body().toString();
            errorList = jsonErrorMessageConvertor.readValue(result, StandardErrorList.class);
        } catch (Exception exception) {
            LOGGER.error(PARSING_ERROR_BY_CODE, exception.getMessage());
        }

//        switch (response.status()){
//            case 400:
//                return new OpenAPIException(OPENAPI_BAD_REQUEST.getFailureDescription(),httpStatus,internalServerError);
//            case 404:
//                return new OpenAPIException(OPENAPI_RECORD_NOT_FOUND.getFailureDescription(),httpStatus,internalServerError);
//            case 401:
//                return new OpenAPIException(AUTHORIZATION_FAILURE.getFailureDescription(),httpStatus,internalServerError);
//            case 500:
//                return new OpenAPIException(OPENAPI_INTERNAL_SERVER_ERROR.getFailureDescription(),httpStatus,internalServerError);
//            case 503:
//                return new OpenAPIException(SERVER_UNAVAILABLE.getFailureDescription(), httpStatus,internalServerError);
//            default:
//            {
//                if(errorList.getErrors().isEmpty()){
//                    return new OpenAPIException(EXCEPTION_CALLING_OPEN_WEATHER_API,httpStatus,internalServerError);
//                }
//                else{
//                    return new OpenAPIException(EXCEPTION_CALLING_OPEN_WEATHER_API,httpStatus,errorList.getErrors().get(0));
//                }
//            }
//        }

        return new OpenAPIException(EXCEPTION_CALLING_OPEN_WEATHER_API, httpStatus, errorList.getErrors().get(0));

    }

}
