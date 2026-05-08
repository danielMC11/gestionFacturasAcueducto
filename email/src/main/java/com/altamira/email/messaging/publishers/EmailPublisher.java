package com.altamira.email.messaging.publishers;


import com.altamira.common.events.email.PasswordUpdateEmailFailedEvent;
import com.altamira.common.events.email.PasswordUpdateEmailSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.users.exchange}")
    private String exchange;

    @Value("${rabbitmq.users.handle.response.password.reset.success.routing.key}")
    private String successRoutingKey;

    @Value("${rabbitmq.users.handle.response.password.reset.failed.routing.key}")
    private String failedRoutingKey;


    public void publishPasswordUpdateEmailSentEvent(PasswordUpdateEmailSentEvent event) {
        rabbitTemplate.convertAndSend(exchange, successRoutingKey, event);
        log.info("Event {} published successfully for SagaId: {}",
                event.getEventType(), event.getSagaId());
    }

    public void publishPasswordUpdateEmailFailedEvent(PasswordUpdateEmailFailedEvent event){
        rabbitTemplate.convertAndSend(exchange, failedRoutingKey, event);
        log.error("Failure event {} published for SagaId: {}. Error: {}",
                event.getEventType(), event.getSagaId(), event.getErrorMessage());
    }

}
