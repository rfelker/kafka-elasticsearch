package de.codecentric.kes;

import de.codecentric.kes.config.ProducerConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class ProducerTask implements Callback{

    private static final Logger log = LoggerFactory.getLogger(ProducerTask.class);

    @Autowired
    private ProducerConfig config;
    private KafkaProducer<String, String> producer;

    @PostConstruct
    private void initProducer() {
        producer = new KafkaProducer<>(config.producerProperties());
    }

    @Scheduled(fixedDelayString = "${producertask.fixedDelay}")
    public void produceMessages() {

        String value = "time_" + new Date().getTime();

        log.info("sending message with value=" + value);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(config.getTopic(), "key", value);
        producer.send(producerRecord, this);
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        log.info(String.format("onCompletion: metadata=%s, exception=%s", metadata, exception));

    }
}
