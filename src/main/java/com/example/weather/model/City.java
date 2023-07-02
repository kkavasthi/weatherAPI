package com.example.weather.model;

import lombok.Data;

@Data
public class City {

    private int id;
    private String name;
    private String country;
    private int timezone;
}
