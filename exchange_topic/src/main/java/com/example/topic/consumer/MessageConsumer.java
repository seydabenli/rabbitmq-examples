package com.example.topic.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

  @RabbitListener(queues = "${rabbit.queue.topic.name1}")
  public void handleMessage1(String message) {
    System.out.println("Message success received queue-1.." + message);
  }

  @RabbitListener(queues = "${rabbit.queue.topic.name2}")
  public void handleMessage2(String message) {
    System.out.println("Message success received queue-2.." + message);
  }

}
