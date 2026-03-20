package com.example.gestionAcueducto.events;

import java.time.LocalDateTime;

public record ResetPasswordResponseEvent(
        String sagaId,
        String email,
        boolean success,
        LocalDateTime timestamp
) {

    public ResetPasswordResponseEvent {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
