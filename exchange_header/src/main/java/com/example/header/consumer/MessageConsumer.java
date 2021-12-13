package com.example.header.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

  @RabbitListener(queues = "#{rabbitMQConfig.allQueues}")
  public void handleMessage(String message) {

    System.out.println("Message success received queue.." + message);
  }

}
