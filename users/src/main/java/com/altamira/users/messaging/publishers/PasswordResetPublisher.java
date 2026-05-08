package com.altamira.users.messaging.publishers;

import com.altamira.users.events.users.PasswordUpdateRequestedEvent;
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


    @Value("${rabbitmq.users.exchange}")
    private String exchange;

    @Value("${rabbitmq.email.send.password.reset.routing.key}")
    private String routingKey;


    public void publishPasswordUpdateRequestedEvent(PasswordUpdateRequestedEvent event) {

        rabbitTemplate.convertAndSend(exchange, routingKey, event);

        log.info("Event {} published successfully for SagaId: {}",
                event.getEventType(), event.getSagaId());
    }

}
