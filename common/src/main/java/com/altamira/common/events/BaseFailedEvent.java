package com.altamira.common.events;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public abstract class BaseFailedEvent extends BaseEvent {
    private String errorMessage;

    protected BaseFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId); // Envía el ID a BaseEvent
        this.errorMessage = errorMessage;
    }


}