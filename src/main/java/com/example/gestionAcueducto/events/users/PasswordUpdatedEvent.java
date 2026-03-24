package com.example.gestionAcueducto.events.users;

import com.example.gestionAcueducto.events.BaseEvent;

import java.util.UUID;

public class PasswordUpdatedEvent extends BaseEvent {

    public PasswordUpdatedEvent(UUID sagaId) {
        super(sagaId);
    }

}
