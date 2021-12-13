package com.example.topic.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

  @Value("${rabbit.queue.topic.name1}")
  private String queue1;

  @Value("${rabbit.queue.topic.name2}")
  private String queue2;

  @Value("${rabbit.queue.topic.name3}")
  private String queue3;

  @Value("${rabbit.routing.name1}")
  private String routing1;


  @Value("${rabbit.routing.name2}")
  private String routing2;

  @Value("${rabbit.routing.name3}")
  private String routing3;


  @Value("${rabbit.exchange.name}")
  private String exchangeName;

  @Bean
  public Queue queue1() {
    return new Queue(queue1, true);
  }

  @Bean
  public Queue queue2() {
    return new Queue(queue2, true);
  }

  @Bean
  public Queue queue3() {
    return new Queue(queue3, true);
  }


  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(exchangeName);
  }

  @Bean
  public Binding binding1(final Queue queue1, final TopicExchange topicExchange) {
    return BindingBuilder.bind(queue1).to(topicExchange).with(routing1);
  }

  @Bean
  public Binding binding2(final Queue queue2, final TopicExchange topicExchange) {
    return BindingBuilder.bind(queue2).to(topicExchange).with(routing2);
  }

  @Bean
  public Binding binding3(final Queue queue3, final TopicExchange topicExchange) {
    return BindingBuilder.bind(queue3).to(topicExchange).with(routing3);
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
