package de.codecentric.kes;

import de.codecentric.kes.config.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ConsumerTask {

    private static final Logger log = LoggerFactory.getLogger(ConsumerTask.class);

    @Autowired
    private ConsumerConfig config;
    private KafkaConsumer<String, String> consumer;


    @PostConstruct
    private void initConsumer() {
        consumer = new KafkaConsumer<>(config.consumerProperties());
        consumer.subscribe(config.getTopics());
    }

    @PreDestroy
    private void destroyConsumer(){
        if (consumer != null) {
            consumer.close();
        }
    }

    @Scheduled(fixedDelayString = "${consumertask.fixedDelay}")
    public void consumeMessages() {

        ConsumerRecords<String, String> records = consumer.poll(100);

        if(records.isEmpty()){
            return;
        }

        log.info(String.format("consuming %d messages...", records.count()));
        records.forEach(this::consumeRecord);
    }

    private void consumeRecord(ConsumerRecord<String, String> record) {
            log.info(String.format("message: offset=%d, key=%s, value=%s", record.offset(), record.key(), record.value()));
    }
}
