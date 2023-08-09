package com.example.weather.controller;

import com.example.weather.exception.OpenAPIException;
import com.example.weather.response.CityWeatherAPIResponse;
import com.example.weather.response.CityWeatherResponse;
import com.example.weather.service.cityapi.CityForecastServiceImpl;
import com.example.weather.service.weatherdetail.WeatherDetailImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

   @InjectMocks
    private static WeatherController weatherController;
   @Mock
    private WeatherDetailImpl weatherDetail;
   @Mock
    private CityForecastServiceImpl cityForecastService;
   ObjectMapper mapper;
   protected MockMvc mvc;

   @Before
    public void setUp(){
       mapper = new ObjectMapper();
       MockitoAnnotations.initMocks(this);
       mvc = MockMvcBuilders.standaloneSetup(weatherController).build();
   }

   @Test
    public void getWeatherPrediction_Success() throws OpenAPIException {
       String city = "mumbai";


       CityWeatherResponse foreCast = new CityWeatherResponse();
       foreCast.setCity("mumbai");
       foreCast.setCountry("IN");
       foreCast.setCityWeatherAPIResponseList(buildCityWeatherResponse());

       Mockito.when(cityForecastService.getWeatherData(city)).thenReturn(foreCast);

       ResponseEntity<String> responseEntity = weatherController.getWeatherData(new HttpHeaders(),city);

       Assert.assertNotNull(responseEntity);

   }

   @Test
    public void getWeatherPredictionCity_NotProvided() throws Exception{

           MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/weather/v1/prediction/"))
                   .andExpect(status().is4xxClientError()).andReturn();

           Assertions.assertEquals("",mvcResult.getResponse().getContentAsString());
   }

   private List<CityWeatherAPIResponse> buildCityWeatherResponse(){

       List<CityWeatherAPIResponse> cityWeatherAPIResponse = new ArrayList<>();
        List<CityWeatherAPIResponse> results = new ArrayList<>();

        CityWeatherAPIResponse response = new CityWeatherAPIResponse();
           response.setDate("2023-07-02");
           response.setHighTemperature(314.05);
           response.setWindSpeed(10);
           response.setLowTemperature(299.50);
               List<String> userActions = new ArrayList<>();
               userActions.add("Use sunscreen lotion");
               userActions.add("It’s too windy, watch out!");
           response.setRain(false);

          results.add(response);

       CityWeatherAPIResponse response1 = new CityWeatherAPIResponse();
       response1.setDate("2023-07-03");
       response1.setHighTemperature(300.05);
       response1.setWindSpeed(10);
       response1.setLowTemperature(299.50);
       List<String> userActions1 = new ArrayList<>();
       userActions1.add("It’s too windy, watch out!");
       userActions1.add("Carry umbrella");
       response1.setRain(true);

       results.add(response1);

       CityWeatherAPIResponse response2 = new CityWeatherAPIResponse();
       response1.setDate("2023-07-04");
       response1.setHighTemperature(305.05);
       response1.setWindSpeed(9);
       response1.setLowTemperature(299.50);
       List<String> userActions2 = new ArrayList<>();
       userActions2.add("Carry umbrella");
       response1.setRain(true);

       results.add(response2);

      return cityWeatherAPIResponse;

    }

}
