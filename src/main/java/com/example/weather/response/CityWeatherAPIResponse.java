package com.example.weather.response;

import com.example.weather.constants.WeatherConstants;
import com.example.weather.model.ActionForUser;
import com.example.weather.model.Main;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CityWeatherAPIResponse {

    private String date;
    private double highTemperature;
    private double lowTemperature;
    private double windSpeed;
    private List<String> userAction;
    private boolean isRain;
    private boolean isThunderstorm;

    public CityWeatherAPIResponse(double highTemperature, double lowTemperature, double windSpeed) {
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.windSpeed = windSpeed;
    }

    public CityWeatherAPIResponse() {

    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public List<String> getAction() {

        List<String> actions = new ArrayList<>();
        if (highTemperature > WeatherConstants.Max_High_temp) {
            actions.add(ActionForUser.USE_SUN_SCREEN.getAction());
        }
        if (getWindSpeed() > WeatherConstants.windSpeed) {
            actions.add(ActionForUser.WINDY.getAction());
        }

        if (isRain) {
            actions.add(ActionForUser.CARRY_UMBRELLA.getAction());
        }
        if (isThunderstorm) {
            actions.add(ActionForUser.THUNDERSTORM.getAction());
        }
        return actions;
    }

}
