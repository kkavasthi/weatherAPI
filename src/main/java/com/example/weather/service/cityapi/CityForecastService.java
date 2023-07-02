package com.example.weather.service.cityapi;

import com.example.weather.response.CityWeatherAPIResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CityForecastService {


    List<CityWeatherAPIResponse> getWeatherData(String cityName);

}
