package com.example.weather.model;

import lombok.Getter;

@Getter
public enum ActionForUser {

    CARRY_UMBRELLA("Carry umbrella"),
    USE_SUN_SCREEN("Use sunscreen lotion"),
    WINDY("It’s too windy, watch out!"),
    THUNDERSTORM("Don’t step out! A Storm is brewing!");

    private String action;

    ActionForUser(String action) {
        this.action = action;
    }

}
