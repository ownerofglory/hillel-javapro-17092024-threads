package ua.ithillel.thread;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.ithillel.thread.counter.*;
import ua.ithillel.thread.senderreceiver.Message;
import ua.ithillel.thread.senderreceiver.Receiver;
import ua.ithillel.thread.senderreceiver.Sender;
import ua.ithillel.thread.server.*;
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
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

public class Application {
    private static volatile int value; // not cached
    private static volatile boolean ready; // not cached


    public static void main(String[] args) {

        try {
            // factory method
            Server threadPoolServer = Servers.createThreadPoolServer(8080, 2);

            // factory
            ServerFactory serverFactory = new ServerFactory();
            Server server = serverFactory.createServer(ServerType.SINGLE_THREADED, 8080);


            // abstract factory
            AbstractServerFactory abstractServerFactory = new AbstractServerFactory();

            ServerFactory multiThreadedServerFactory = abstractServerFactory.createMultiThreadedServerFactory();
            Server threadPoolServer2 = multiThreadedServerFactory.createServer(ServerType.MULTI_THREADED, 8080);

            ServerFactory singleThreadedServerFactory = abstractServerFactory.createSingleThreadedServerFactory();
            Server singleThreadedServer = singleThreadedServerFactory.createServer(ServerType.SINGLE_THREADED, 8080);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPoolExecutor = Executors.newFixedThreadPool(2);

        ExecutorService executorService = Executors.newCachedThreadPool();


        String file1 = "./files/file1.txt";
        String file2 = "./files/file2.txt";
        String file3 = "./files/file3.txt";

        try {
            Callable<String> reader1 = () -> {
                try {
                    String text = readTextFile(file1);
                    Thread.sleep(8000);
                    return text;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            };
            Callable<String> reader2 = () -> readTextFile(file2);
            Callable<String> reader3 = () -> readTextFile(file3);

//            Future<String> future1 = fixedThreadPoolExecutor.submit(reader1);
//            Future<String> future2 = fixedThreadPoolExecutor.submit(reader2);
//            Future<String> future3 = fixedThreadPoolExecutor.submit(reader3);

            List<Callable<String>> readerTasks = List.of(reader1, reader2, reader3);

            String firstResult = fixedThreadPoolExecutor.invokeAny(readerTasks);

            List<Future<String>> results = fixedThreadPoolExecutor.invokeAll(readerTasks);

//            Iterator<Future<String>> iterator = results.iterator();
//            while (iterator.hasNext()) {
//                Future<String> next = iterator.next();
//                if (next.isDone()) {
//                    System.out.println(next.get());
//                    iterator.remove();
//                }
//            }


            fixedThreadPoolExecutor.shutdown();

//            System.out.println(shutdown);

//            FutureTask<String> ft1 = new FutureTask<>(reader1);
//            FutureTask<String> ft2 = new FutureTask<>(reader2);
//            FutureTask<String> ft3 = new FutureTask<>(reader3);





//            String text1 = ft1.get();
//            String text2 = ft2.get();
//            String text3 = ft3.get();

//            while (!ft1.isDone()) {
//                System.out.println("Excpeccting task 1");
//                System.out.println(ft1.state());
//            }
//
//            String text1 = ft1.get();

            System.out.println();

//            System.out.printf("%s\n%s\n%s\n", text1, text2, text3);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


//        System.out.println("Starting application");
//
//                String apiKey = System.getenv("OPENWEATHER_API_KEY");
//        HttpClient httpClient = HttpClient
//                .newBuilder()
//                .build();
//
//
//        WeatherClient weatherClient = new OpenWeatherClient(apiKey, httpClient, new ObjectMapper());
//        WeatherManager weatherManager = new WeatherManager(weatherClient);
//
//        Scanner scanner = new Scanner(System.in);
//
//        Queue<Message> messageQueue = new LinkedList<>();
//
//        Exchanger<Weather> exchanger = new Exchanger<>();
//
//        Receiver receiver1 = new Receiver(messageQueue, weatherManager, exchanger);
//        Receiver receiver2 = new Receiver(messageQueue, weatherManager, exchanger);
//        Receiver receiver3 = new Receiver(messageQueue, weatherManager, exchanger);
//        Receiver receiver4 = new Receiver(messageQueue, weatherManager, exchanger);
//
//        Sender sender = new Sender(messageQueue, exchanger);
//
//        new Thread(receiver1).start();
//        new Thread(receiver2).start();
//        new Thread(receiver3).start();
//        new Thread(receiver4).start();
//
//        while (scanner.hasNext()) {
//            try {
//                String city = scanner.nextLine();
//
//                Message message = new Message(city);
//
//                sender.sendMessage(message);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }



//        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
//            System.out.println("Cyclic barrier reached");
//        });
//
//        Runnable counter1 = () -> {
//            try {
//                for (int i = 0; i < 100; i++) {
//                    System.out.println("First counter:" + i);
//                    cyclicBarrier.await();
//                }
//            } catch (BrokenBarrierException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        };
//
//        Runnable counter2 = () -> {
//            try {
//                for (int i = 0; i < 100; i++) {
//                    System.out.println("Second counter:" + i);
//                    cyclicBarrier.await();
//                }
//            } catch (BrokenBarrierException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        };
//
//        new Thread(counter1).start();
//        new Thread(counter2).start();


//        String file1 = "./files/file1.txt";
//        String file2 = "./files/file2.txt";
//        String file3 = "./files/file3.txt";



//        try {
//            // wait group
//            CountDownLatch countDownLatch = new CountDownLatch(3);
//
//            int count = 0;
//            StringBuffer stringBuffer1 = new StringBuffer();
//
//            Runnable r1 = () -> {
//                try {
//                    String text = readTextFile(file1);
//
//                    Thread.sleep(10);
//                    stringBuffer1.append(text);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                } finally {
//                    countDownLatch.countDown();
//                }
//            };
//
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
//                } finally {
//                    countDownLatch.countDown();
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
//                } finally {
//                    countDownLatch.countDown();
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
//            countDownLatch.await();
//
//            System.out.println(stringBuffer1.append(stringBuffer2).append(stringBuffer3).toString());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


//        Counter counter = new Counter();
//        LockedCounter counter = new LockedCounter();
//        RWLockedCounter counter = new RWLockedCounter();
//        SemaphoreCounter counter = new SemaphoreCounter();
//            AtomicCounter counter = new AtomicCounter();

            // race condition

//            Runnable r1 = () -> {
//                for (int i = 0; i < 200_000; i++) {
//                    System.out.println("Writer 1: " + counter.incrementAndGet());
//                }
//            };
//
//            Runnable r2 = () -> {
//                for (int i = 0; i < 200_000; i++) {
//                    System.out.println("Writer 2: " + counter.incrementAndGet());
//                }
//            };
//
//            Runnable r3 = () -> {
//                for (int i = 0; i < 200_000; i++) {
//                    System.out.println("Reader1 thread: " + counter.getCount());
//                }
//            };
//
//            Runnable r4 = () -> {
//                for (int i = 0; i < 200_000; i++) {
//                    System.out.println("Reader2 thread: " + counter.getCount());
//                }
//            };
//
//            new Thread(r1).start();
//            new Thread(r2).start();
//            new Thread(r3).start();
//            new Thread(r4).start();
//
//        System.out.println();


//        Runnable r = () -> {
//            while (!ready) {
//                Thread.yield();
//            }
//
//            System.out.println("Thread: " + value);
//        };
//        new Thread(r).start();
//
//
//        value = 12;
//        ready = true;



//        Runnable task = () -> System.out.println("Thread");
//
//        new Thread(task).start();
//        new Thread(task).start();
//        new Thread(task).start();

//        String apiKey = System.getenv("OPENWEATHER_API_KEY");
//        HttpClient httpClient = HttpClient
//                .newBuilder()
//                .build();
//
//
//        WeatherClient weatherClient = new OpenWeatherClient(apiKey, httpClient, new ObjectMapper());
//        WeatherManager weatherManager = new WeatherManager(weatherClient);
//
//        Scanner scanner = new Scanner(System.in);
//
//        Queue<Message> messageQueue = new LinkedList<>();
//
//        Receiver receiver1 = new Receiver(messageQueue, weatherManager);
//        Receiver receiver2 = new Receiver(messageQueue, weatherManager);
//        Receiver receiver3 = new Receiver(messageQueue, weatherManager);
//        Receiver receiver4 = new Receiver(messageQueue, weatherManager);
//
//        Sender sender = new Sender(messageQueue);
//
//        new Thread(receiver1).start();
//        new Thread(receiver2).start();
//        new Thread(receiver3).start();
//        new Thread(receiver4).start();
//
//        while (scanner.hasNext()) {
//            try {
//                String city = scanner.nextLine();
//
//                Message message = new Message(city);
//
//                sender.sendMessage(message);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }




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
