package com.altamira.common.events.users;

import com.altamira.common.events.BaseFailedEvent;


import java.util.UUID;



public class PasswordUpdateFailedEvent extends BaseFailedEvent {

    public PasswordUpdateFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId, errorMessage);
    }


}
