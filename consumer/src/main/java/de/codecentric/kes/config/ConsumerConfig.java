package de.codecentric.kes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class ConsumerConfig {

    @Value("${consumertask.topics}")
    private List<String> topics;

    @Autowired
    private Environment env;

    public Properties consumerProperties() {

        Properties properties = new Properties();

        List<String> keys = Arrays.asList(
                org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.apache.kafka.clients.consumer.ConsumerConfig.CLIENT_ID_CONFIG);

        keys.forEach(key -> properties.put(key, env.getProperty(key)));
        return properties;
    }

    public List<String> getTopics() {
        return topics;
    }

}
