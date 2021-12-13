package com.example.fanout.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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

  @Value("${rabbit.queue.fanout1.name}")
  private String queue1;

  @Value("${rabbit.queue.fanout2.name}")
  private String queue2;

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
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(exchangeName);
  }

  @Bean
  public Binding bindingLog1(final Queue queue1, final FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(queue1).to(fanoutExchange);
  }

  @Bean
  public Binding bindingLog2(final Queue queue2, final FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(queue2).to(fanoutExchange);
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
