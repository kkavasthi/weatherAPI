package com.example.weather.controller;

import com.example.weather.exception.OpenAPIException;
import com.example.weather.exception.WeatherPredictionExceptionEnum;
import com.example.weather.model.WeatherResponse;
import com.example.weather.response.CityWeatherResponse;
import com.example.weather.service.cityapi.CityForecastService;
import com.example.weather.util.WeatherAPIExceptionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/weather/v1/")
public class WeatherController {

    @Autowired
    CityForecastService cityForecastService;

    public WeatherController(CityForecastService cityWeatherService ){
        this.cityForecastService = cityWeatherService;
    }

    @GetMapping(value = "/prediction/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWeatherData(@RequestHeader HttpHeaders requestHeaders,
                                                 @PathVariable(value = "city") String cityName)  {
        String response = null;
        try {
            CityWeatherResponse  filteredWeatherResponse = cityForecastService.getWeatherData(cityName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            WeatherResponse weatherResponse = new WeatherResponse();
            weatherResponse.setWeatherResponse(filteredWeatherResponse);
            response = mapper.writeValueAsString(weatherResponse);
        } catch (Throwable throwable) {
            if ((throwable instanceof CompletionException) || (throwable instanceof ExecutionException) && (throwable.getCause() instanceof OpenAPIException)) {
                OpenAPIException f = (OpenAPIException) throwable.getCause();
                throw f;
            } else if (throwable instanceof OpenAPIException) {
                throw (OpenAPIException) throwable;
            }
            else{
                WeatherAPIExceptionUtil.throwException(WeatherPredictionExceptionEnum.INTERNAL_SERVER_ERROR,"Exception in getting Weather API response");
            }

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
