package ua.ithillel.thread.weather.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {
    private String name;
    private OpenWeatherMainWeather main;
    private OpenWeatherDescription[] weather;

    public OpenWeatherMainWeather getMain() {
        return main;
    }

    public void setMain(OpenWeatherMainWeather main) {
        this.main = main;
    }

    public OpenWeatherDescription[] getWeather() {
        return weather;
    }

    public void setWeather(OpenWeatherDescription[] weather) {
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
