package com.example.gestionAcueducto.users.messaging.listeners;

import com.example.gestionAcueducto.events.users.PasswordUpdateFailedEvent;
import com.example.gestionAcueducto.events.users.PasswordUpdatedEvent;
import com.example.gestionAcueducto.users.enums.EmailStatus;
import com.example.gestionAcueducto.users.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetListener {

    private final PasswordResetTokenService passwordResetTokenService;

    @RabbitListener(queues = "${rabbitmq.password.reset.response.queue}")
    public void onPasswordUpdatedEvent(PasswordUpdatedEvent event){
        passwordResetTokenService.updatePasswordResetTokenStatus(event.getSagaId(), EmailStatus.SENT);
    }

    @RabbitListener(queues = "${rabbitmq.password.reset.response.queue}")
    public void onPasswordUpdateFailedEvent(PasswordUpdateFailedEvent event){
        passwordResetTokenService.updatePasswordResetTokenStatus(event.getSagaId(), EmailStatus.FAILED);
    }

}
