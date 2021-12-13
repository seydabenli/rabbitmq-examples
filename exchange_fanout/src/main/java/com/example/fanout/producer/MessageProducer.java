package com.example.fanout.producer;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer {

  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @PostConstruct
  public void init() {
    String message = "Fanout exchange ile tüm queuelara mesaj geldi....";
    sendToQueue(message);
  }

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendToQueue(String message) {
    rabbitTemplate.convertAndSend(exchangeName, "", message);
  }

}
