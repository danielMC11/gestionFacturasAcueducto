package com.altamira.users.events.email;

import com.altamira.users.events.BaseFailedEvent;

import java.util.UUID;


public class PasswordUpdateEmailFailedEvent extends BaseFailedEvent {

    public PasswordUpdateEmailFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId, errorMessage);
    }
}
