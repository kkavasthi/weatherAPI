package com.example.weather.response;

import com.example.weather.model.City;
import lombok.Data;

import java.util.ArrayList;

@Data
public class WeatherDetailResponse {
    private String cod;
    private int message;
    private int cnt;
    private ArrayList<WeatherResponseList> list;
    private City city;
}
