package com.example.demo.utils;

import com.example.demo.foundation.constants.Dict;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
public class KafkaUtilTest {



    private Duration duration = Duration.ofSeconds(12);

    @Test
    public void test(){


        KafkaConsumer kafkaConsumer = KafkaUtil.createConsumer(Dict.kafkaAddress, Dict.kafka_group);



        KafkaUtil.consumeMessages(kafkaConsumer, Dict.kafka_topic, duration);
    }

}
