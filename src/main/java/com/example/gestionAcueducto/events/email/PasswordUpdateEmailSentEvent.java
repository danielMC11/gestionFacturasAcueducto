package com.example.gestionAcueducto.events.email;

import com.example.gestionAcueducto.events.BaseEvent;

import java.time.LocalDateTime;
import java.util.UUID;


public class PasswordUpdateEmailSentEvent extends BaseEvent {

    public  PasswordUpdateEmailSentEvent(UUID sagaId) {
        super(sagaId);
    }

}
