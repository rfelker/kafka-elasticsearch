package de.codecentric.kes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class ProducerConfig {

    @Value("${producertask.topic}")
    private String topic;

    @Autowired
    private Environment env;

    public Properties producerProperties() {

        Properties properties = new Properties();

        List<String> keys = Arrays.asList(
                org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.BUFFER_MEMORY_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.COMPRESSION_TYPE_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG);

        keys.forEach(key -> properties.put(key, env.getProperty(key)));
        return properties;
    }

    public String getTopic() {
        return topic;
    }
}
