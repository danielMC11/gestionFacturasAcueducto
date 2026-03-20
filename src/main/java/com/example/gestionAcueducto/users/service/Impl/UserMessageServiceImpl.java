package com.example.gestionAcueducto.users.service.Impl;

import com.example.gestionAcueducto.events.ResetPasswordRequestEvent;
import com.example.gestionAcueducto.users.service.UserMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserMessageServiceImpl implements UserMessageService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    private String exchange;

    @Value("${rabbitmq.password.reset.request.routing.key}")
    private String routingKey;


    @Override
    public void sendResetPasswordEmail(ResetPasswordRequestEvent event) {

        rabbitTemplate.convertAndSend(exchange, routingKey, event);

        log.info("Iniciando proceso de envío de email para: {}", event.email());
    }

    @RabbitListener
    public void onResetPasswordResponse(){

    }




}
