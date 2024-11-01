package ua.ithillel.thread.senderreceiver;

import ua.ithillel.thread.weather.exception.WeatherAppException;
import ua.ithillel.thread.weather.manager.WeatherManager;
import ua.ithillel.thread.weather.model.Weather;

import java.util.LinkedList;
import java.util.Queue;

public class Receiver implements Runnable {
    private final Queue<Message> messageQueue;
    private WeatherManager weatherManager;

    public Receiver(Queue<Message> messageQueue, WeatherManager weatherManager) {
        this.messageQueue = messageQueue;
        this.weatherManager = weatherManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
               synchronized (messageQueue) {
                   if (!messageQueue.isEmpty()) {
                       Message message = messageQueue.remove();

                       String city = message.getCity();
                       Weather weather = weatherManager.getWeatherByCity(city);

                       Thread.sleep(2000);

                       System.out.printf("Weather in %s: %fC, %f \n", weather.getName(),
                               weather.getTemperature(),
                               weather.getHumidity());
                   } else {
                       messageQueue.wait();
                   }
               }

            } catch (WeatherAppException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
