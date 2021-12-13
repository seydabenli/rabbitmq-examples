package com.example.defaultt.producer;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

  @PostConstruct
  public void init() throws InterruptedException {
    String test = "Message send....";
    sendToQueue(test);
  }


  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendToQueue(String message) throws InterruptedException {
    rabbitTemplate.convertAndSend(message);
  }

}
