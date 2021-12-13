package com.example.header.producer;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP.BasicProperties;

@Service
public class MessageProducer {

  @Value("${rabbit.exchange.name}")
  private String exchangeName;


  @PostConstruct
  public void init() throws InterruptedException {
    String message="Headers test";
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("kurumAdi", "ERZURUM OLTU DEVLET HASTANESİ");
    BasicProperties properties = new BasicProperties()
        .builder().headers(headerMap).build();
    sendToQueue(properties,message);
  }

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendToQueue(BasicProperties properties, String message) throws InterruptedException {
    rabbitTemplate.convertAndSend(exchangeName,message,properties);
    // Thread.sleep(10000);
  }

}
