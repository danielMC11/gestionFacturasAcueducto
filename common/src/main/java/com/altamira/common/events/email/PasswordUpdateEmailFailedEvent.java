package com.altamira.common.events.email;

import com.altamira.common.events.BaseFailedEvent;

import java.util.UUID;


public class PasswordUpdateEmailFailedEvent extends BaseFailedEvent {

    public PasswordUpdateEmailFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId, errorMessage);
    }
}
