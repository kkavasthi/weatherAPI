package com.example.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather/v1")
public class HealthCheckController {

    @RequestMapping(method= RequestMethod.GET,value = "/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).body("ping success");
    }
}
