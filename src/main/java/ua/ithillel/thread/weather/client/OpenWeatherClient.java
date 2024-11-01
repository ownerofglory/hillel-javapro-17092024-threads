package ua.ithillel.thread.weather.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import ua.ithillel.thread.weather.client.response.OpenWeatherResponse;
import ua.ithillel.thread.weather.exception.WeatherAppException;
import ua.ithillel.thread.weather.exception.WeatherAppSetupException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenWeatherClient implements WeatherClient {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";

    private final String key;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenWeatherClient(String key, HttpClient httpClient, ObjectMapper objectMapper) {
        this.key = key;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public OpenWeatherResponse getCurrentWeatherDataByQuery(String city) throws WeatherAppException, WeatherAppSetupException {
        try {
            city = city.replace(" ", "%20");

            URI uri = new URI(BASE_URL + String.format("/weather?appid=%s&q=%s&units=metric", key, city));

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WeatherAppException("Server returned an error");
            }

            OpenWeatherResponse openWeatherResponse = objectMapper.readValue(response.body(), OpenWeatherResponse.class);

            return openWeatherResponse;

        } catch (URISyntaxException e) {
            System.out.println("Incorrect URL: " + e.getMessage());

            throw new WeatherAppSetupException("Incorrect URL");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error when requesting weather server:" + e.getMessage());

            throw new WeatherAppException("Error when requesting weather server");
        }

    }
}
