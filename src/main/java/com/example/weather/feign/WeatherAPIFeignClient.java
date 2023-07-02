package com.example.weather.feign;

import com.example.weather.response.WeatherDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="weather-open-api",url="${weatherAPIFeignClient.url}",configuration = {HttpSecurity.class})
public interface WeatherAPIFeignClient {


    @RequestMapping(path = "/forecast", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WeatherDetailResponse> getCityWeatherDetail(
            @RequestParam("q") String city,
            @RequestParam("appid") String appId,
            @RequestParam("cnt") int cntValue
    );
}
