package com.example.fanout.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

  @RabbitListener(queues = "${rabbit.queue.fanout1.name}")
  public void handleLog1(String message) {
    System.out.println("Message success received.." + message);
  }

  @RabbitListener(queues = "${rabbit.queue.fanout2.name}")
  public void handleLog2(String message) {
    System.out.println("Message success received.." + message);
  }

}
