package com.example.weather.controller;

import com.example.weather.exception.APIException;
import com.example.weather.exception.WeatherPredictionExceptionEnum;
import com.example.weather.response.CityWeatherAPIResponse;
import com.example.weather.service.cityapi.CityForecastService;
import com.example.weather.util.WeatherAPIExceptionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/weather/v1/")
public class WeatherController {

    @Autowired
    CityForecastService cityWeatherService;

    public WeatherController(CityForecastService cityWeatherService ){
        this.cityWeatherService = cityWeatherService;
    }

    @GetMapping(value = "/prediction/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWeatherData(@RequestHeader HttpHeaders requestHeaders,
                                                 @PathVariable(value = "city") String cityName)  {
        String response = null;
        try {
            List<CityWeatherAPIResponse> filteredWeatherResponse = cityWeatherService.getWeatherData(cityName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            response = mapper.writeValueAsString(filteredWeatherResponse);
        } catch (Throwable throwable) {
            if ((throwable instanceof CompletionException) || (throwable instanceof ExecutionException) && (throwable.getCause() instanceof APIException)) {
                APIException f = (APIException) throwable.getCause();
                throw f;
            } else if (throwable instanceof APIException) {
                throw (APIException) throwable;
            }
            else{
                WeatherAPIExceptionUtil.throwException(WeatherPredictionExceptionEnum.INTERNAL_SERVER_ERROR,"Exception in getting Weather API response");
            }

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
