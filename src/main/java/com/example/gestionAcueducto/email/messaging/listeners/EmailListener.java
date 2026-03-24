package com.example.gestionAcueducto.email.messaging.listeners;

import com.example.gestionAcueducto.email.service.EmailService;
import com.example.gestionAcueducto.events.email.PasswordUpdateEmailFailedEvent;
import com.example.gestionAcueducto.events.email.PasswordUpdateEmailSentEvent;
import com.example.gestionAcueducto.events.users.PasswordUpdateRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {


    private final EmailService emailService;


    @RabbitListener(queues = "${rabbitmq.password.reset.request.queue}")
    public void onPasswordUpdateRequestedEvent(PasswordUpdateRequestedEvent event){

        log.info("Recibida petición de reset de password para el email: {}", event.getEmail());

        try {
            emailService.sendResetPasswordEmail(
                    event.getSagaId(),
                    event.getEmail(),
                    event.getTemplate(),
                    event.getToken());

            log.info("Email enviado exitosamente a {}.", event.getEmail());

        } catch (Exception e) {
            log.error("Error procesando el envío de email para {}: {}", event.getEmail(), e.getMessage());
        }
    }

}
