package com.example.gestionAcueducto.email.service.impl;

import com.example.gestionAcueducto.events.ResetPasswordRequestEvent;
import com.example.gestionAcueducto.email.service.EmailService;
import com.example.gestionAcueducto.email.service.EmailMessageService;


import com.example.gestionAcueducto.events.ResetPasswordResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageServiceImpl implements EmailMessageService {

    private final EmailService emailService;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    private String exchange;

    @Value("${rabbitmq.password.reset.response.routing.key}")
    private String routingKey;

    @RabbitListener(queues = "${rabbitmq.password.reset.request.queue}")
    @Override
    public void onResetPasswordRequest(ResetPasswordRequestEvent resetPasswordRequestEvent) {

        log.info("Recibida petición de reset de password para el email: {}", resetPasswordRequestEvent.email());

        try {
            emailService.sendResetPasswordEmail(
                    resetPasswordRequestEvent.email(),
                    resetPasswordRequestEvent.templateName(),
                    resetPasswordRequestEvent.token());

            log.info("Email enviado exitosamente a {}.", resetPasswordRequestEvent.email());

            rabbitTemplate.convertAndSend(exchange, routingKey,
                    new ResetPasswordResponseEvent(resetPasswordRequestEvent.email(), true, LocalDateTime.now()));

        } catch (Exception e) {
            log.error("Error procesando el envío de email para {}: {}", resetPasswordRequestEvent.email(), e.getMessage());

            rabbitTemplate.convertAndSend(exchange, routingKey,
                    new ResetPasswordResponseEvent(resetPasswordRequestEvent.email(), false, LocalDateTime.now()));
        }
    }

}
