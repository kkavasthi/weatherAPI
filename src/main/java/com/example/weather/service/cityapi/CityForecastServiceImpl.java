package com.example.weather.service.cityapi;

import com.example.weather.constants.WeatherConstants;
import com.example.weather.model.Weather;
import com.example.weather.response.CityWeatherAPIResponse;
import com.example.weather.response.CityWeatherResponse;
import com.example.weather.response.WeatherDetailResponse;
import com.example.weather.response.WeatherResponseList;
import com.example.weather.service.weatherdetail.WeatherDetailImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


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
    public CityWeatherResponse getWeatherData(String city)  {

        CityWeatherResponse cityWeatherResponse = new CityWeatherResponse();

        int cnt = getRecordCount();

        ResponseEntity<WeatherDetailResponse> apiResponse = buildCityWeatherResponse(city, appId, cnt);

        List<CityWeatherAPIResponse> cityWeather = getCityWeather(apiResponse,cnt);

        cityWeatherResponse.setCityWeatherAPIResponseList(cityWeather);

        cityWeatherResponse.setCity(apiResponse.getBody().getCity().getName());

        cityWeatherResponse.setCountry(apiResponse.getBody().getCity().getCountry());

        return cityWeatherResponse;
    }

    private ResponseEntity<WeatherDetailResponse> buildCityWeatherResponse(String city, String appId, int cnt){

            return weatherDetail.getWeatherDetail(city, appId, cnt);

    }

    private List<CityWeatherAPIResponse> getCityWeather(ResponseEntity<WeatherDetailResponse> apiResponse, int cnt){

        List<CityWeatherAPIResponse> predictions = new ArrayList<>();

        List<WeatherResponseList>  nextDaysForecast = apiResponse.getBody().getList().subList(currentDaySlots,cnt);

        for(int i=0;i<nextDaysForecast.size()-1;i=i+WeatherConstants.SLOTS.length){
            CityWeatherAPIResponse prediction = getWeatherDataInfo(nextDaysForecast.subList(i,i+WeatherConstants.SLOTS.length));
            String date = nextDaysForecast.get(i).getDateText().substring(0,10);
            prediction.setDate(date);
            predictions.add(prediction);

        }

        return predictions;
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