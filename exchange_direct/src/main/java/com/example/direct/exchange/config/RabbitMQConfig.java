package com.example.direct.exchange.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

  @Value("${rabbit.queue.success.name}")
  private String queueSuccess;

  @Value("${rabbit.routing.success.name}")
  private String routingSuccess;

  @Value("${rabbit.routing.warn.name}")
  private String routingWarn;

  @Value("${rabbit.queue.error.name}")
  private String queueError;

  @Value("${rabbit.routing.error.name}")
  private String routingError;

  @Value("${rabbit.exchange.name}")
  private String exchangeName;


  @Bean
  public Queue queueSuccess() {
    return new Queue(queueSuccess, true);
  }

  @Bean
  public Queue queueError() {
    return new Queue(queueError, true);
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(exchangeName);
  }

  @Bean
  public Binding bindingSuccess(final Queue queueSuccess, final DirectExchange directExchange) {
    return BindingBuilder.bind(queueSuccess).to(directExchange).with(routingSuccess);
  }

  @Bean
  public Binding bindingError(final Queue queueError, final DirectExchange directExchange) {
    return BindingBuilder.bind(queueError).to(directExchange).with(routingError);
  }

  @Bean
  public Binding bindingWarn(final Queue queueSuccess, final DirectExchange directExchange) {
    return BindingBuilder.bind(queueSuccess).to(directExchange).with(routingWarn);
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
