package com.altamira.common.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public abstract class BaseEvent {
    private UUID sagaId;
    private LocalDateTime createdAt;
    private String eventType;

    public BaseEvent(){}

    // El constructor de la superclase hace el trabajo automático
    protected BaseEvent(UUID sagaId) {
        this.sagaId = sagaId;
        this.createdAt = LocalDateTime.now(); // Se genera solo al instanciar
        this.eventType = this.getClass().getSimpleName(); // Toma el nombre de la clase hija
    }


}