package ua.ithillel.thread.weather.manager;


import ua.ithillel.thread.weather.client.WeatherClient;
import ua.ithillel.thread.weather.client.response.OpenWeatherResponse;
import ua.ithillel.thread.weather.exception.WeatherAppException;
import ua.ithillel.thread.weather.model.Weather;

public class WeatherManager {
    private final WeatherClient weatherClient;

    public WeatherManager(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public Weather getWeatherByCity(String city) throws WeatherAppException, Exception {
        OpenWeatherResponse currentWeatherDataByQuery = weatherClient.getCurrentWeatherDataByQuery(city);

        double temp = currentWeatherDataByQuery.getMain().getTemp();
        double humidity = currentWeatherDataByQuery.getMain().getHumidity();

        Weather weather = new Weather();
        weather.setTemperature(temp);
        weather.setHumidity(humidity);
        weather.setName(currentWeatherDataByQuery.getName());

        String iconUrl = String.format("https://openweathermap.org/img/wn/%s@2x.png", currentWeatherDataByQuery.getWeather()[0].getIcon());
        weather.setIconUrl(iconUrl);

        String description = currentWeatherDataByQuery.getWeather()[0].getMain() + ": " + currentWeatherDataByQuery.getWeather()[0].getDescription();
        weather.setDescription(description);

        return weather;
    }
}
