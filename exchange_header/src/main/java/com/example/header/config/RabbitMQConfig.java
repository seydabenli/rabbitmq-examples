package com.example.header.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder.HeadersExchangeMapConfigurer.HeadersExchangeMapBindingCreator;
import org.springframework.amqp.core.HeadersExchange;
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

  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @Bean
  public HeadersExchange headersExchange() {
    return new HeadersExchange(exchangeName);
  }

  List<String> allQueues = new ArrayList<>();

  @Bean
  public void createQueue() {
    Map<String, String> kurumList = KurumService.getKurumList();
    kurumList.entrySet().stream().forEach(k -> {
      Queue queue = new Queue(k.getValue(), true);

      declareBinding(queue, k.getKey(), k.getValue());
    });
  }


  public HeadersExchangeMapBindingCreator declareBinding(Queue queue, String key, String value) {
    Map<String, Object> healthArgs = new HashMap<>();
    healthArgs.put("x-match", "all");
    healthArgs.put(key, value);
    return BindingBuilder.bind(queue).to(headersExchange()).whereAll(healthArgs);
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
