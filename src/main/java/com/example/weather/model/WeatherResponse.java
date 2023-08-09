package com.example.weather.model;

import com.example.weather.response.CityWeatherResponse;
import lombok.Data;

@Data
public class WeatherResponse {
    CityWeatherResponse weatherResponse;
}
