package org.avro;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        String topic = "avro-kafka";
        String schemaRegistry = "http://localhost:8081";
        String kafkaServer = "localhost:9092";

        Producer producer = new Producer(topic, kafkaServer, schemaRegistry);
        ConsumerApp consumerApp = new ConsumerApp(topic, kafkaServer, schemaRegistry);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        try {
            executor.scheduleAtFixedRate(producer::produce, 0, 1, TimeUnit.SECONDS);
            consumerApp.consume();
        } finally {
            producer.shutdown();
        }
    }
}
