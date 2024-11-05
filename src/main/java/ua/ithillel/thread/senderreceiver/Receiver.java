package ua.ithillel.thread.senderreceiver;

import ua.ithillel.thread.weather.exception.WeatherAppException;
import ua.ithillel.thread.weather.manager.WeatherManager;
import ua.ithillel.thread.weather.model.Weather;

import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Receiver implements Runnable {
    private final Queue<Message> messageQueue;
    private WeatherManager weatherManager;
    private Exchanger<Weather> exchanger;

    public Receiver(Queue<Message> messageQueue, WeatherManager weatherManager, Exchanger<Weather> exchanger) {
        this.messageQueue = messageQueue;
        this.weatherManager = weatherManager;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message;
               synchronized (messageQueue) {
                   if (messageQueue.isEmpty()) {
                       messageQueue.wait();
                   }

                   message = messageQueue.remove();
               }
                String city = message.getCity();
                Weather weather = weatherManager.getWeatherByCity(city);
                Thread.sleep(2000);

                exchanger.exchange(weather);

            } catch (WeatherAppException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
