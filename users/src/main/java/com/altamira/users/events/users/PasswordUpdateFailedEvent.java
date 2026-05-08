package com.altamira.users.events.users;

import com.altamira.users.events.BaseFailedEvent;

import java.util.UUID;



public class PasswordUpdateFailedEvent extends BaseFailedEvent {

    public PasswordUpdateFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId, errorMessage);
    }


}
