package com.example.dlq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @Value("${rabbit.exchange.deadletter.name}")
  private String deadletterExchangeName;

  @Value("${rabbit.queue.dlq.name}")
  private String deadletterQueue;

  @Value("${rabbit.queue.name}")
  private String queueName;

  @Value("${rabbit.routing.dql.name}")
  private String routingDlq;

  @Value("${rabbit.routing.name}")
  private String routingName;

  @Bean
  DirectExchange deadLetterExchange() {
    return new DirectExchange(deadletterExchangeName);
  }

  @Bean
  DirectExchange exchange() {
    return new DirectExchange(exchangeName);
  }

  @Bean
  Queue queue_dlq() {
    return QueueBuilder.durable(deadletterQueue).build();

  }

  @Bean
  Queue queue() {
    return QueueBuilder.durable(queueName).deadLetterExchange(deadletterExchangeName).deadLetterRoutingKey(routingDlq).build();
  }

  @Bean
  Binding queueDlqBinding() {
    return BindingBuilder.bind(queue_dlq()).to(deadLetterExchange()).with(routingDlq);

  }

  @Bean
  Binding binding() {
    return BindingBuilder.bind(queue()).to(exchange()).with(routingName);

  }

  @Bean
  @ConditionalOnMissingBean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  @ConditionalOnMissingBean
  public AmqpTemplate template(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }


}
