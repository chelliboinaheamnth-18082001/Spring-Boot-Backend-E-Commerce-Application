package com.example.Notification.Service.Custom_Configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${RabbitMq.queue.name}")
    private String queueName;
    @Value("${RabbitMq.exchange.name}")
    private String exchange;
    @Value("${RabbitMq.routing.name}")
    private String routingKey;


    @Bean
    public Queue queue()
    {
        return QueueBuilder.durable(queueName)
            .build();
    }

    @Bean
    public TopicExchange exchange()
    {
        return ExchangeBuilder.topicExchange(exchange)
                .durable(true)
                .build();
    }

    @Bean
    public Binding binding()
    {
        return BindingBuilder.bind(queue()).
                to(exchange()).
                with(routingKey);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory)
    {
        RabbitAdmin rabbitAdmin=new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public MessageConverter messageConverter()
    {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate template=new  RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        template.setExchange(exchange);
        return  template;

    }



}
