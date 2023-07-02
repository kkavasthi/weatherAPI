//package com.example.weather.service;
//
//import com.example.weather.feign.WeatherAPIFeignClient;
//import com.example.weather.response.CityWeatherAPIResponse;
//import com.example.weather.response.WeatherDetailResponse;
//import com.example.weather.service.cityapi.CityForecastServiceImpl;
//import com.example.weather.service.weatherdetail.WeatherDetailImpl;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.anything;
//import static reactor.core.publisher.Mono.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CityForecastServiceImplTest {
//
//    @InjectMocks
//    private CityForecastServiceImpl cityForecastServiceImpl;
//
//    @Mock
//    private HttpHeaders httpHeaders;
//
//    @Mock
//    private WeatherAPIFeignClient weatherAPIFeignClient;
//
//    @Mock
//    private static WeatherDetailImpl weatherDetailImpl;
//
//    @BeforeClass
//    public static void setup(){
//        CityForecastServiceImpl cityForecastService = new CityForecastServiceImpl(weatherDetailImpl);
//
//    }
//
//    @Test
//    public void getCityWeatherForProvidedCity() throws Exception {
//        String city = "mumbai";
//        String appId = "d2929e9483efc82c82c32ee7e02d563e" ;
//        int cnt = 26;
//
//        List<CityWeatherAPIResponse> expectedWeather =  getWeatherResponse();
//
//
//        List<CityWeatherAPIResponse> cityWeatherAPIResponseList = cityForecastServiceImpl.getWeatherData(city);
//
//        Assert.assertEquals(expectedWeather.size(),cityWeatherAPIResponseList.size());
//
//    }
//
//    private List<CityWeatherAPIResponse> getWeatherResponse(){
//
//
//            List<CityWeatherAPIResponse> cityWeatherAPIResponse = new ArrayList<>();
//            List<CityWeatherAPIResponse> results = new ArrayList<>();
//
//            CityWeatherAPIResponse response = new CityWeatherAPIResponse();
//            response.setDate("2023-07-02");
//            response.setHighTemperature(314.05);
//            response.setWindSpeed(10);
//            response.setLowTemperature(299.50);
//            List<String> userActions = new ArrayList<>();
//            userActions.add("Use sunscreen lotion");
//            userActions.add("It’s too windy, watch out!");
//            response.setRain(false);
//
//            results.add(response);
//
//            CityWeatherAPIResponse response1 = new CityWeatherAPIResponse();
//            response1.setDate("2023-07-03");
//            response1.setHighTemperature(300.05);
//            response1.setWindSpeed(10);
//            response1.setLowTemperature(299.50);
//            List<String> userActions1 = new ArrayList<>();
//            userActions1.add("It’s too windy, watch out!");
//            userActions1.add("Carry umbrella");
//            response1.setRain(true);
//
//            results.add(response1);
//
//            CityWeatherAPIResponse response2 = new CityWeatherAPIResponse();
//            response1.setDate("2023-07-04");
//            response1.setHighTemperature(305.05);
//            response1.setWindSpeed(9);
//            response1.setLowTemperature(299.50);
//            List<String> userActions2 = new ArrayList<>();
//            userActions2.add("Carry umbrella");
//            response1.setRain(true);
//
//            results.add(response2);
//
//            return cityWeatherAPIResponse;
//
//            }
//}
