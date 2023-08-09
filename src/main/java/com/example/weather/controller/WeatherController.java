package com.example.weather.controller;

import com.example.weather.exception.OpenAPIException;
import com.example.weather.exception.WeatherPredictionExceptionEnum;
import com.example.weather.exception.decoder.OpenAPIDecoder;
import com.example.weather.model.WeatherResponse;
import com.example.weather.response.CityWeatherResponse;
import com.example.weather.service.cityapi.CityForecastService;
//import com.example.weather.util.WeatherAPIExceptionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

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
        } catch (Exception exception) {
            LOGGER.error("Error while calling Open API", exception);
           if(HttpStatus.NOT_FOUND.is4xxClientError()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not available");
             } else if (HttpStatus.METHOD_NOT_ALLOWED.is4xxClientError()) {
               return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Operation is not allowed");
           } else if (HttpStatus.BAD_REQUEST.is4xxClientError()) {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested Parameter are incorrect");
           } else{
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Requested Parameter are incorrect");
           }

//            switch (Instance Of){
//                case NOT_FOUND:
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not available");
//                case BAD_REQUEST:
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested Parameter are incorrect");
//                case INTERNAL_SERVER_ERROR:
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Application server is not available");
            }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
