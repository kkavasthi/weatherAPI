package com.example.weather.service.cityapi;

import com.example.weather.constants.WeatherConstants;
import com.example.weather.exception.APIException;
import com.example.weather.model.ActionForUser;
import com.example.weather.model.StandardError;
import com.example.weather.model.Weather;
import com.example.weather.response.CityWeatherAPIResponse;
import com.example.weather.response.WeatherDetailResponse;
import com.example.weather.response.WeatherResponseList;
import com.example.weather.service.weatherdetail.WeatherDetail;
import com.example.weather.service.weatherdetail.WeatherDetailImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.weather.exception.WeatherPredictionExceptionEnum.OPENAPI_INTERNAL_SERVER_ERROR;
import static com.example.weather.model.ActionForUser.USE_SUN_SCREEN;


@Service
public class CityForecastServiceImpl implements CityForecastService {

    @Value("${weather-app-api-key}")
    private String appId;

    @Value("${no-of-days}")
    private int noOfDays;

    @Value("${timezone}")
    private String timeZone;
    private int currentDaySlots;

    private final WeatherDetailImpl weatherDetail;


    public CityForecastServiceImpl(WeatherDetailImpl weatherDetail){
        this.weatherDetail = weatherDetail;
    }
    @Override
    public List<CityWeatherAPIResponse> getWeatherData(String city)  {

        List<CityWeatherAPIResponse> predictions = new ArrayList<>();
        int cnt = getRecordCount();

        ResponseEntity<WeatherDetailResponse> apiResponse = buildCityWeatherResponse(city, appId, cnt);
        List<WeatherResponseList>  nextDaysForecast = apiResponse.getBody().getList().subList(currentDaySlots,cnt);

        for(int i=0;i<nextDaysForecast.size()-1;i=i+WeatherConstants.SLOTS.length){
            CityWeatherAPIResponse prediction = getWeatherDataInfo(nextDaysForecast.subList(i,i+WeatherConstants.SLOTS.length));
            String date = nextDaysForecast.get(i).getDateText().substring(0,10);
            prediction.setDate(date);
            predictions.add(prediction);
        }
        return predictions;
    }

    private ResponseEntity<WeatherDetailResponse> buildCityWeatherResponse(String city, String appId, int cnt){

            return weatherDetail.getWeatherDetail(city, appId, cnt);

    }


    private int getRecordCount(){

        DateFormat dateFormat = new SimpleDateFormat("HH");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        String newDate = dateFormat.format(new Date());
        int currHour = Integer.parseInt(newDate);
        currentDaySlots = (int) Arrays.stream(WeatherConstants.SLOTS).filter(slot -> slot > currHour).count();

        return currentDaySlots + (WeatherConstants.SLOTS.length * noOfDays);
    }

    private CityWeatherAPIResponse getWeatherDataInfo(List<WeatherResponseList> list){

        double highTemp = Integer.MIN_VALUE;
        double lowTemp = Integer.MAX_VALUE;
        double maxWindSpeed = Integer.MIN_VALUE;
        boolean isRain = false;
        boolean isThunderstorm = false;

        for(WeatherResponseList data : list){

            if(lowTemp > data.getMain().getTempMin()) {
                lowTemp = data.getMain().getTempMin();
            }

            if(highTemp < data.getMain().getTempMax()) {
                highTemp = data.getMain().getTempMax();
            }

            if(maxWindSpeed < data.getWind().getSpeed()) {
                maxWindSpeed = data.getWind().getSpeed();
            }
            if(!isRain){
                isRain = weatherById(data.getWeather(), WeatherConstants.RAIN_ID);

            }
            if(!isThunderstorm){
                isThunderstorm = weatherById(data.getWeather(),WeatherConstants.THUNDER_ID);
            }
        }

        CityWeatherAPIResponse weatherResponse = new CityWeatherAPIResponse(highTemp,lowTemp,maxWindSpeed);

        weatherResponse.setRain(isRain);
        weatherResponse.setThunderstorm(isThunderstorm);
        return weatherResponse;

    }

    private boolean weatherById(List<Weather> weatherList, int startId ){
        return weatherList.stream().anyMatch(weather -> weather.getId() >= startId && weather.getId() < startId + 100);
    }


}