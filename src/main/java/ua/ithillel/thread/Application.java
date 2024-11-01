package ua.ithillel.thread;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.ithillel.thread.counter.AtomicCounter;
import ua.ithillel.thread.counter.Counter;
import ua.ithillel.thread.senderreceiver.Message;
import ua.ithillel.thread.senderreceiver.Receiver;
import ua.ithillel.thread.senderreceiver.Sender;
import ua.ithillel.thread.server.MultiThreadedBlockingServer;
import ua.ithillel.thread.thread.MyCounterThread;
import ua.ithillel.thread.thread.MyDaemonThread;
import ua.ithillel.thread.thread.MyRunnableThread;
import ua.ithillel.thread.weather.client.OpenWeatherClient;
import ua.ithillel.thread.weather.client.WeatherClient;
import ua.ithillel.thread.weather.exception.WeatherAppException;
import ua.ithillel.thread.weather.manager.WeatherManager;
import ua.ithillel.thread.weather.model.Weather;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting application");

        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        HttpClient httpClient = HttpClient
                .newBuilder()
                .build();


        WeatherClient weatherClient = new OpenWeatherClient(apiKey, httpClient, new ObjectMapper());
        WeatherManager weatherManager = new WeatherManager(weatherClient);

        Scanner scanner = new Scanner(System.in);

        Queue<Message> messageQueue = new LinkedList<>();

        Receiver receiver1 = new Receiver(messageQueue, weatherManager);
        Receiver receiver2 = new Receiver(messageQueue, weatherManager);
        Receiver receiver3 = new Receiver(messageQueue, weatherManager);
        Receiver receiver4 = new Receiver(messageQueue, weatherManager);

        Sender sender = new Sender(messageQueue);

        new Thread(receiver1).start();
        new Thread(receiver2).start();
        new Thread(receiver3).start();
        new Thread(receiver4).start();

        while (scanner.hasNext()) {
            try {
                String city = scanner.nextLine();

                Message message = new Message(city);

                sender.sendMessage(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }




//        try (MultiThreadedBlockingServer server = new MultiThreadedBlockingServer(8000)) {
//            server.start();
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


//        try {
////            Counter counter = new Counter();
//            AtomicCounter counter = new AtomicCounter();
//
//            // race condition
//
//            Runnable r1 = () -> {
//                for (int i = 0; i < 200_000; i++) {
//                    System.out.println("Thread 1: " + counter.incrementAndGet());
//                }
//            };
//
//            Runnable r2 = () -> {
//                for (int i = 0; i < 200_000; i++) {
//                    System.out.println("Thread 2: " + counter.incrementAndGet());
//                }
//            };
//
//            Thread thread1 = new Thread(r1);
//            Thread thread2 = new Thread(r2);
//
//            thread1.start();
//            thread2.start();
//
//            thread1.join();
//            thread2.join();
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


//        String file1 = "./files/file1.txt";
//        String file2 = "./files/file2.txt";
//        String file3 = "./files/file3.txt";
//
//
//
//        try {
//            StringBuffer stringBuffer1 = new StringBuffer();
//            Runnable r1 = () -> {
//                try {
//                    String text = readTextFile(file1);
//
//                    Thread.sleep(10);
//                    stringBuffer1.append(text);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//
//            StringBuffer stringBuffer2 = new StringBuffer();
//            Runnable r2 = () -> {
//                try {
//                    String text = readTextFile(file2);
//
//                    Thread.sleep(10);
//                    stringBuffer2.append(text);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//
//            StringBuffer stringBuffer3 = new StringBuffer();
//            Runnable r3 = () -> {
//                try {
//                    String text = readTextFile(file3);
//
//                    Thread.sleep(10);
//                    stringBuffer3.append(text);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//
//            Thread thread1 = new Thread(r1);
//            Thread thread2 = new Thread(r2);
//            Thread thread3 = new Thread(r3);
//
//            thread1.start();
//            thread2.start();
//            thread3.start();
//
//            thread1.join();
//            thread2.join();
//            thread3.join();
//
//
//            System.out.println(stringBuffer1.toString() + stringBuffer2.toString() + stringBuffer3.toString());
//
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//
//        }



        System.out.println();

//        Thread currentThread = Thread.currentThread();
//        int priority = currentThread.getPriority();
//        final String name = currentThread.getName();
//
//        MyCounterThread myCounterThread = new MyCounterThread("my-counter");
//        myCounterThread.setPriority(10);
////        myCounterThread.run(); // no thread is created
//        myCounterThread.start(); // thread is created, run() method is executed
//
//
//        MyRunnableThread myRunnable = new MyRunnableThread("my-runnable");
//        Thread myRunnableThread = new Thread(myRunnable);
//        myRunnableThread.setPriority(Thread.MIN_PRIORITY);
//        myRunnableThread.start();
//
//        MyDaemonThread myDaemonThread = new MyDaemonThread();
//        Thread thread = new Thread(myDaemonThread, name);
//        thread.setDaemon(true);
//        thread.start();
//
//
//        // lambda - function :: starting from java 8
//        Runnable r1 = () -> {
//            // closure of 'name'
//            System.out.println("Created by: " + name);
//
//            for (int i = 0; i < 10_000; i++) {
//                System.out.println("Runnable 1: " + i);
//            }
//        };
//        new Thread(r1).start();
//        new Thread(r1).start();
//        new Thread(r1).start();
//
//        try {
//
//            for (int i = 0; i < 1000; i++) {
//                Thread.sleep(1);
//                System.out.printf("Main: %d\n", i);
//            }
//
//        } catch (InterruptedException e) {
//            System.out.printf("%s\n", e.getMessage());
//        }

//        myCounterThread.interrupt();
//        myRunnableThread.interrupt();

//        String apiKey = System.getenv("OPENWEATHER_API_KEY");
//
//        HttpClient httpClient = HttpClient
//                .newBuilder()
//                .build();
//
//
//        WeatherClient weatherClient = new OpenWeatherClient(apiKey, httpClient, new ObjectMapper());
//        WeatherManager weatherManager = new WeatherManager(weatherClient);


        System.out.println("Application finished");
    }

    private static String readTextFile(String fileName) {
        try (
                FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            Optional<String> result = bufferedReader.lines().reduce((acc, cur) -> acc + cur + "\n");

            return result.orElse("");

        } catch (IOException e) {
           return "";
        }
    }
}
