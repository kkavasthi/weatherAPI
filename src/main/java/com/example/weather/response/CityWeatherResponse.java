package com.example.weather.response;

import lombok.Data;

import java.util.List;

@Data
public class CityWeatherResponse {

    private String city;
    private String country;

    private List<CityWeatherAPIResponse> cityWeatherAPIResponseList;
}
