package com.example.gestionAcueducto.events;

import java.util.UUID;

public abstract class BaseFailedEvent extends BaseEvent {
    private final String errorMessage;

    protected BaseFailedEvent(UUID sagaId, String errorMessage) {
        super(sagaId); // Envía el ID a BaseEvent
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() { return errorMessage; }
}