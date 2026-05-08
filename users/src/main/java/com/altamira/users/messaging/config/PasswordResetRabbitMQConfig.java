package com.altamira.users.messaging.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PasswordResetRabbitMQConfig {



    @Value("${rabbitmq.users.handle.response.password.reset.success.queue}")
    private String successQueue;
    @Value("${rabbitmq.users.handle.response.password.reset.success.routing.key}")
    private String successRoutingKey;

    @Value("${rabbitmq.users.handle.response.password.reset.failed.queue}")
    private String failedQueue;
    @Value("${rabbitmq.users.handle.response.password.reset.failed.routing.key}")
    private String failedRoutingKey;


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Queue handleResponsePasswordResetSuccessQueue() {
        return QueueBuilder.durable(successQueue)
                .build();
    }

    @Bean
    public Queue handleResponsePasswordResetFailedQueue() {
        return QueueBuilder.durable(failedQueue)
                .build();
    }


    @Bean
    public Binding bindHandleResponsePasswordResetSuccessQueue(Queue handleResponsePasswordResetSuccessQueue, DirectExchange userExchange) {
        return BindingBuilder
                .bind(handleResponsePasswordResetSuccessQueue)
                .to(userExchange)
                .with(successRoutingKey); // Los mensajes enviados con esta clave llegarán a esta cola
    }


    @Bean
    public Binding bindHandleResponsePasswordResetFailedQueue(Queue handleResponsePasswordResetFailedQueue, DirectExchange userExchange) {
        return BindingBuilder
                .bind(handleResponsePasswordResetFailedQueue)
                .to(userExchange)
                .with(failedRoutingKey); // Los mensajes enviados con esta clave llegarán a esta cola
    }


}