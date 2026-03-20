package com.example.gestionAcueducto.users.config;


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
    @Value("${rabbitmq.password.reset.response.queue}")
    private String queue;
    @Value("${rabbitmq.password.reset.response.routing.key}")
    private String routingKey;

    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(exchange);
    }


    @Bean
    public Queue passwordResetResponseQueue() {
        return QueueBuilder.durable(queue)
                .build();
    }





    @Bean
    public Binding bindPasswordResetResponse(Queue passwordResetResponseQueue, DirectExchange emailExchange) {
        return BindingBuilder
                .bind(passwordResetResponseQueue)
                .to(emailExchange)
                .with(routingKey); // Los mensajes enviados con esta clave llegarán a esta cola
    }
}