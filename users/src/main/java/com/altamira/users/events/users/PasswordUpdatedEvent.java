package com.altamira.users.events.users;

import com.altamira.users.events.BaseEvent;

import java.util.UUID;


public class PasswordUpdatedEvent extends BaseEvent {


    public PasswordUpdatedEvent(UUID sagaId) {
        super(sagaId);
    }
}
