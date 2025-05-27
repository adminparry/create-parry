package com.example.demo.foundation.thread;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class Index {

    @Autowired
    private KafkaProducer kafkaProducer;

    private static final Object lock = new Object();

    private static AtomicInteger i = new AtomicInteger(0);


    public static void main(String[] args) {

        System.out.println("starting");


        new Thread(() -> {
            while (i.get() <= 200) {
                synchronized (lock) {

                    lock.notify();

                    System.out.println(Thread.currentThread().getName() + ":" + i.getAndIncrement());

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, "a").start();

        new Thread(() -> {
            while (i.get() <= 200) {
                synchronized (lock) {

                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + ":" + i.getAndIncrement());

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, "Thread-B").start();
    }

}
