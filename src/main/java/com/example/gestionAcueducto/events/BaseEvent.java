package com.example.gestionAcueducto.events;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEvent {
    private final UUID sagaId;
    private final LocalDateTime createdAt;
    private final String eventType;

    // El constructor de la superclase hace el trabajo automático
    protected BaseEvent(UUID sagaId) {
        this.sagaId = sagaId;
        this.createdAt = LocalDateTime.now(); // Se genera solo al instanciar
        this.eventType = this.getClass().getSimpleName(); // Toma el nombre de la clase hija
    }

    // Getters (Solo lectura para mantener la inmutabilidad)
    public UUID getSagaId() { return sagaId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getEventType() { return eventType; }
}