package com.altamira.users.messaging.listeners;

import com.altamira.users.events.email.PasswordUpdateEmailFailedEvent;
import com.altamira.users.events.email.PasswordUpdateEmailSentEvent;
import com.altamira.users.enums.EmailStatus;
import com.altamira.users.service.Impl.SseService;
import com.altamira.users.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetListener {

    private final PasswordResetTokenService passwordResetTokenService;
    private final SseService sseService;

    @RabbitListener(queues = "${rabbitmq.users.handle.response.password.reset.success.queue}")
    public void onPasswordUpdateEmailSentEvent(PasswordUpdateEmailSentEvent event){
        log.info("Received successful event {} for SagaId: {}", event.getEventType(), event.getSagaId());

        passwordResetTokenService.updatePasswordResetTokenStatus(event.getSagaId(), EmailStatus.SENT);
        sseService.sendNotification(event.getSagaId().toString(), "saga-completed", "SUCCESS");

    }

    @RabbitListener(queues = "${rabbitmq.users.handle.response.password.reset.failed.queue}")
    public void onPasswordUpdateFailedEvent(PasswordUpdateEmailFailedEvent event){
        log.error("Received failure event {} for SagaId: {}", event.getEventType(), event.getSagaId());

        passwordResetTokenService.updatePasswordResetTokenStatus(event.getSagaId(), EmailStatus.FAILED);
        sseService.sendNotification(event.getSagaId().toString(), "saga-failed", "FAILED");


    }

}
