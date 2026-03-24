package com.example.gestionAcueducto.events.users;

import com.example.gestionAcueducto.events.BaseFailedEvent;

import java.util.UUID;

public class PasswordUpdateFailedEvent extends BaseFailedEvent {


    public PasswordUpdateFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId, errorMessage);
    }


}
