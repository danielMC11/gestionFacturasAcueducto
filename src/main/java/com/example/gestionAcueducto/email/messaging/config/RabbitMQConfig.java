package com.example.gestionAcueducto.email.messaging.config;


import org.springframework.amqp.core.*;
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
    public Queue passwordUpdateRequestQueue() {
        return QueueBuilder.durable(queue)
                .build();
    }


    @Bean
    public Binding bindPasswordUpdateRequest(Queue passwordUpdateRequestQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(passwordUpdateRequestQueue)
                .to(emailExchange)
                .with(routingKey); // Los mensajes enviados con esta clave llegarán a esta cola
    }

}