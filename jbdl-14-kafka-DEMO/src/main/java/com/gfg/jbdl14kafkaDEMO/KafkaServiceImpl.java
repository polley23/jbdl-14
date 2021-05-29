package com.gfg.jbdl14kafkaDEMO;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaServiceImpl {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    public void publish(Message message){
        kafkaTemplate.send(message.getTopic(),message.getMessage());
    }

    @KafkaListener(topics = "test", groupId = "test")
    public void read(String message){
        log.info("message received in application test as {}",message);
    }

    @KafkaListener(topics = "test", groupId = "test1")
    public void read1(String message){
        log.info("message received in application test1 as {}",message);
    }

}
