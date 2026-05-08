package com.altamira.common.events.email;

import com.altamira.common.events.BaseEvent;

import java.util.UUID;


public class PasswordUpdateEmailSentEvent extends BaseEvent {

    public PasswordUpdateEmailSentEvent(){}

    public PasswordUpdateEmailSentEvent(UUID sagaId) {
        super(sagaId);
    }
}
