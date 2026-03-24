package com.example.gestionAcueducto.events.email;

import com.example.gestionAcueducto.events.BaseEvent;
import com.example.gestionAcueducto.events.BaseFailedEvent;

import java.util.UUID;

public class PasswordUpdateEmailFailedEvent extends BaseFailedEvent {

    public PasswordUpdateEmailFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId, errorMessage);
    }

}
