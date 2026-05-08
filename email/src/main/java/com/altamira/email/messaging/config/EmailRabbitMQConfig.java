package com.altamira.email.messaging.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailRabbitMQConfig {


    @Value("${rabbitmq.email.send.password.reset.queue}")
    private String queue;
    @Value("${rabbitmq.email.send.password.reset.routing.key}")
    private String routingKey;


    @Bean
    public Queue sendPasswordResetQueue() {
        return QueueBuilder.durable(queue)
                .build();
    }

    @Bean
    public Binding bindSendPasswordResetQueue(Queue sendPasswordResetQueue, DirectExchange userExchange) {
        return BindingBuilder
                .bind(sendPasswordResetQueue)
                .to(userExchange)
                .with(routingKey); // Los mensajes enviados con esta clave llegarán a esta cola
    }

}