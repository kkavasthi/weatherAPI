package com.example.weather.service.cityapi;

import com.example.weather.response.CityWeatherResponse;

public interface CityForecastService {


    CityWeatherResponse getWeatherData(String cityName);

}
