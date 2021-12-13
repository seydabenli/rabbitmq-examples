package com.example.direct.exchange.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.direct.exchange.config.HttpStatus;

@Service
public class LogConsumer {

  @RabbitListener(queues = "${rabbit.queue.success.name}")
  public void handleLogSuccess(HttpStatus status) {
    System.out.println("Log success received..");
    System.out.println(status);
  }

  @RabbitListener(queues = "${rabbit.queue.error.name}")
  public void handleLogError(HttpStatus status) {
    System.out.println("Log error received..");
    System.out.println(status);
  }
}
