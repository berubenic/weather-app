package com.mycompany.app;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherData {

    @JsonProperty("weather-data")
    private Map<String, List<DailyWeather>> weatherData;

    public Map<String, List<DailyWeather>> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Map<String, List<DailyWeather>> weatherData) {
        this.weatherData = weatherData;
    }
}