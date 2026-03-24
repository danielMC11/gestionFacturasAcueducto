package com.example.gestionAcueducto.users.messaging.publishers;

import com.example.gestionAcueducto.events.users.PasswordUpdateRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetPublisher {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchange.email.name}")
    private String exchange;

    @Value("${rabbitmq.password.reset.request.routing.key}")
    private String routingKey;


    public void publishPasswordUpdateRequestedEvent(PasswordUpdateRequestedEvent event) {

        rabbitTemplate.convertAndSend(exchange, routingKey, event);

        log.info("Iniciando proceso de envío de email para: {}", event.getEmail());
    }


}
