package com.altamira.users.events.email;

import com.altamira.users.events.BaseEvent;

import java.util.UUID;


public class PasswordUpdateEmailSentEvent extends BaseEvent {

    public PasswordUpdateEmailSentEvent(){}

    public PasswordUpdateEmailSentEvent(UUID sagaId) {
        super(sagaId);
    }
}
