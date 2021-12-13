package com.example.dlq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.dlq.config.Employee;
import com.example.dlq.config.InvalidSalaryException;

@Service
public class MessageConsumer {

  @RabbitListener(queues = "${rabbit.queue.name}")
  public void handleLogSuccess(Employee employee) throws InvalidSalaryException {
    System.out.println("Message success received.." + employee);
    if (employee.getSalary() < 0) {
      throw new InvalidSalaryException();
    }
  }
}
