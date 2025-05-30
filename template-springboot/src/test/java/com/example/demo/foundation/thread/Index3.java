package com.example.demo.foundation.thread;

import com.example.demo.foundation.constants.Dict;
import com.example.demo.utils.KafkaUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class Index3 {


    private static final Object lock = new Object();

    private static AtomicInteger i = new AtomicInteger(0);


    private static final KafkaProducer kafkaProducer = KafkaUtil.createProducer(Dict.kafkaAddress);
    public static void main(String[] args) {


        new Thread(() -> {
            while (i.get() <= 200) {
                synchronized (lock) {

                    lock.notify();

                    System.out.println(Thread.currentThread().getName() + ":" + i.getAndIncrement());
                    KafkaUtil.sendMessage(kafkaProducer, Dict.kafka_topic, "message content");

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
