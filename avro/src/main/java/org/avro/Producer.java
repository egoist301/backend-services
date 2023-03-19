package org.avro;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.avro.service.Greeting;

public class Producer {
    private static final String SCHEMA_REGISTRY_URL = "schema.registry.url";
    private static final String KEY = "key";
    private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());
    private final KafkaProducer<String, Greeting> kafkaProducer;
    private final String topic;

    public Producer(String topic, String kafkaServer, String schemaRegistry) {
        this.topic = topic;
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        properties.put(SCHEMA_REGISTRY_URL, schemaRegistry);

        this.kafkaProducer = new KafkaProducer<>(properties);
    }

    /**
     * To produce a message to sending it to a Kafka server
     */
    public void produce() {
        Greeting greeting = Greeting.newBuilder()
                .setGreet("Hello")
                .setTime(System.currentTimeMillis())
                .build();

        ProducerRecord<String, Greeting> record = new ProducerRecord<>(topic, KEY, greeting);
        kafkaProducer.send(record);
        LOGGER.log(Level.INFO, "Sending Record: {0}", greeting.getGreet());
    }

    /**
     * Shutting down producer after it is finishing it is work
     */
    public void shutdown() {
        kafkaProducer.flush();
        kafkaProducer.close();
        LOGGER.log(Level.INFO, "Shutting down Kafka Producer");
    }
}
