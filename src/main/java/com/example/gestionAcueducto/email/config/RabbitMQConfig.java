package com.example.gestionAcueducto.email.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.email.name}")
    private String exchange;
    @Value("${rabbitmq.password.reset.request.queue}")
    private String queue;
    @Value("${rabbitmq.password.reset.request.routing.key}")
    private String routingKey;


    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(exchange);
    }


    @Bean
    public Queue passwordResetRequestQueue() {
        return QueueBuilder.durable(queue)
                .build();
    }


    @Bean
    public Binding bindPasswordResetRequest(Queue passwordResetRequestQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(passwordResetRequestQueue)
                .to(emailExchange)
                .with(routingKey); // Los mensajes enviados con esta clave llegarán a esta cola
    }

}