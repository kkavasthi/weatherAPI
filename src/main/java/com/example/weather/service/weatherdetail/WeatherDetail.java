package com.example.weather.service.weatherdetail;

import com.example.weather.response.WeatherDetailResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface WeatherDetail {

    ResponseEntity<WeatherDetailResponse> getWeatherDetail(String city, String apiId, int cnt);
}
