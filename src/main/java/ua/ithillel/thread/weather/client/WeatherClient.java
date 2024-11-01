package ua.ithillel.thread.weather.client;


import ua.ithillel.thread.weather.client.response.OpenWeatherResponse;

public interface WeatherClient {
    OpenWeatherResponse getCurrentWeatherDataByQuery(String city) throws Exception;
}
