package com.example.demo.foundation.thread_pool;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Index {


    ThreadPoolExecutor messageConsumerExecutor = new ThreadPoolExecutor(
            5,
            10,
            30L,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @KafkaListener(topics = "order-topic")
    public void consumer(ConsumerRecord<String, String> record) {
        messageConsumerExecutor.execute(() -> {
            try {

            }catch (Exception e) {
                System.out.println("处理消息失败" + e);
//                sendToDLQ(record);
            }
        });
    }

    static ThreadPoolExecutor httpRequestExecutor = new ThreadPoolExecutor(
            50,
            200,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1000),
            new NamedThreadFactory("http-req-pool"),
            new ThreadPoolExecutor.CallerRunsPolicy()

    );
}
