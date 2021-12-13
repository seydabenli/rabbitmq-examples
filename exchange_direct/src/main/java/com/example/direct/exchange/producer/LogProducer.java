package com.example.direct.exchange.producer;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.direct.exchange.config.HttpStatus;

@Service
public class LogProducer {

  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @PostConstruct
  public void init() throws InterruptedException {
    HttpStatus status= HttpStatus.of(400);
     sendToQueue(status);
  /*try {
      Thread t = new Thread();
      t.start();
      sendToQueue(status);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
  }

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendToQueue(HttpStatus status) throws InterruptedException {
    System.out.println("HttpStatus : " + status);
    String route=status.getMessageType();
    rabbitTemplate.convertAndSend(exchangeName, route, status);
   // Thread.sleep(10000);
  }


}
