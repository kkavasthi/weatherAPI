package com.example.weather.service.weatherdetail;

import com.example.weather.feign.WeatherAPIFeignClient;
import com.example.weather.response.WeatherDetailResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WeatherDetailImpl implements WeatherDetail{

    private WeatherAPIFeignClient weatherAPIFeignClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDetailImpl.class);

    public WeatherDetailImpl (){}

    @Autowired
    public WeatherDetailImpl(WeatherAPIFeignClient weatherAPIFeignClient){
        this.weatherAPIFeignClient = weatherAPIFeignClient;
    }


     @Override
    public ResponseEntity<WeatherDetailResponse> getWeatherDetail(String city, String apiId, int cnt) {


        ResponseEntity<WeatherDetailResponse> response = weatherAPIFeignClient.getCityWeatherDetail(city, apiId, cnt);


        LOGGER.info("Successfully send request to OpenAPIResponse. Response from OpenAPI httpCode:{} and Response:{}", response.getStatusCode(),response.getBody());

        return response;
    }
}
