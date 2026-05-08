package com.altamira.common.events.users;

import com.altamira.common.events.BaseEvent;

import java.util.UUID;


public class PasswordUpdatedEvent extends BaseEvent {


    public PasswordUpdatedEvent(UUID sagaId) {
        super(sagaId);
    }
}
