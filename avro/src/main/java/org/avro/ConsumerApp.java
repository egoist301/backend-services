package org.avro;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerApp {

    private static final Logger LOGGER = Logger.getLogger(ConsumerApp.class.getName());

    private static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

    private static final String KAFKA_AVRO_DESERIALIZER = "io.confluent.kafka.serializers.KafkaAvroDeserializer";

    private static final String SCHEMA_REGISTRY_URL = "schema.registry.url";

    private static final String GROUP_ID = "group1";

    private static final String EARLIEST = "earliest";

    private final Consumer<String, GenericRecord> consumer;

    private final String topic;

    public ConsumerApp(String topic, String kafkaServer, String schemaRegistry) {
        this.topic = topic;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KAFKA_AVRO_DESERIALIZER);
        properties.put(SCHEMA_REGISTRY_URL, schemaRegistry);

        this.consumer = new KafkaConsumer<>(properties);
    }

    /**
     * Consuming a specific message from Kafka server
     */
    public void consume() {
        consumer.subscribe(List.of(topic));
        try {
            while (true) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    LOGGER.log(Level.INFO, "Record Value:: {0}", record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
