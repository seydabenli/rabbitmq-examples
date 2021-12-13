package com.example.dlq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dlq.config.Employee;


@RestController
@RequestMapping(value = "/message/")
public class MessageController {


  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @Value("${rabbit.routing.name}")
  private String routingName;


  @Autowired
  private RabbitTemplate rabbitTemplate;

  @GetMapping(value = "/producer")
  public String producer(@RequestParam("empName") String empName, @RequestParam("empId") String empId, @RequestParam("salary") int salary) {
    Employee employee = new Employee();
    employee.setEmpId(empId);
    employee.setEmpName(empName);
    employee.setSalary(salary);

    rabbitTemplate.convertAndSend(exchangeName, routingName, employee);
    return "Message sent to the RabbitMQ Successfully" + employee.getSalary();
  }
}
