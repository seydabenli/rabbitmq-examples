package com.example.defaultt.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {


  @RabbitListener(queues = "${rabbit.queue.name}")
  public void handleLogSuccess(String message) {
    System.out.println("Message success received.."+message);
  }
}
