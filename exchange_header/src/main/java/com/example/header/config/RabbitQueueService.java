package com.example.header.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitQueueService implements IRabbitQueueService {

  @Autowired
  private RabbitAdmin rabbitAdmin;

  @Autowired
  private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

  @Override
  public void addNewQueue(String queueName, String exchangeName, String routingKey) {
    Queue queue = new Queue(queueName, true, false, false);
    Binding binding = new Binding(
        queueName,
        Binding.DestinationType.EXCHANGE,
        exchangeName,
        routingKey,
        null
    );
    rabbitAdmin.declareQueue(queue);
    rabbitAdmin.declareBinding(binding);
    this.addQueueToListener(exchangeName,queueName);
  }

  @Override
  public void addQueueToListener(String listenerId, String queueName) {
    if (!checkQueueExistOnListener(listenerId,queueName)) {
      this.getMessageListenerContainerById(listenerId).addQueueNames(queueName);
      System.out.println("queue ");
    } else {
      System.out.println("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
    }
  }

  @Override
  public void removeQueueFromListener(String listenerId, String queueName) {
    System.out.println("removing queue : " + queueName + " from listener : " + listenerId);
    if (checkQueueExistOnListener(listenerId,queueName)) {
      this.getMessageListenerContainerById(listenerId).removeQueueNames(queueName);
      System.out.println("deleting queue from rabbit management");
      this.rabbitAdmin.deleteQueue(queueName);
    } else {
      System.out.println("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
    }
  }

  @Override
  public Boolean checkQueueExistOnListener(String listenerId, String queueName) {
    try {
      System.out.println("checking queueName : " + queueName + " exist on listener id : " + listenerId);
      System.out.println("getting queueNames");
      String[] queueNames = this.getMessageListenerContainerById(listenerId).getQueueNames();
      System.out.println("queueNames : " +  queueNames);
      if (queueNames != null) {
        System.out.println("checking " + queueName + " exist on active queues");
        for (String name : queueNames) {
          System.out.println("name : " + name + " with checking name : " + queueName);
          if (name.equals(queueName)) {
            System.out.println("queue name exist on listener, returning true");
            return Boolean.TRUE;
          }
        }
        return Boolean.FALSE;
      } else {
        System.out.println("there is no queue exist on listener");
        return Boolean.FALSE;
      }
    } catch (Exception e) {
      System.out.println("Error on checking queue exist on listener");
      return Boolean.FALSE;
    }
  }

  private AbstractMessageListenerContainer getMessageListenerContainerById(String listenerId) {
    System.out.println("getting message listener container by id : " + listenerId);
    return ((AbstractMessageListenerContainer) this.rabbitListenerEndpointRegistry
        .getListenerContainer(listenerId)
    );
  }
}
