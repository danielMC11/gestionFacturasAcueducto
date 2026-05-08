package com.altamira.email.messaging.listeners;

import com.altamira.common.events.users.PasswordUpdateRequestedEvent;
import com.altamira.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {


    private final EmailService emailService;


    @RabbitListener(queues = "${rabbitmq.email.send.password.reset.queue}")
    public void onPasswordUpdateRequestedEvent(PasswordUpdateRequestedEvent event){

        log.info("Received event {} for SagaId: {}", event.getEventType(), event.getSagaId());

        emailService.sendResetPasswordEmail(
                    event.getSagaId(),
                    event.getEmail(),
                    event.getTemplate(),
                    event.getToken());

    }

}
