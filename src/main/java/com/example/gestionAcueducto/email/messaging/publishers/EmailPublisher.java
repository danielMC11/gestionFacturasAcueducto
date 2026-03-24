package com.example.gestionAcueducto.email.messaging.publishers;


import com.example.gestionAcueducto.events.email.PasswordUpdateEmailFailedEvent;
import com.example.gestionAcueducto.events.email.PasswordUpdateEmailSentEvent;
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

    @Value("${rabbitmq.exchange.email.name}")
    private String exchange;

    @Value("${rabbitmq.password.reset.response.routing.key}")
    private String routingKey;


    public void publishPasswordUpdateEmailSentEvent(PasswordUpdateEmailSentEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }

    public void publishPasswordUpdateEmailFailedEvent(PasswordUpdateEmailFailedEvent event){
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }


}
