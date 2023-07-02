package com.example.weather.response;

import com.example.weather.model.Main;
import com.example.weather.model.Weather;
import com.example.weather.model.Wind;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class WeatherResponseList {
    private int dt;
    private Main main;
    private ArrayList<Weather> weather;
    private Wind wind;
    @JsonProperty("dt_txt")
    private String dateText;
}
