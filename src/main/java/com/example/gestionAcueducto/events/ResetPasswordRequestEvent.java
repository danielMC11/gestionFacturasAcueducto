package com.example.gestionAcueducto.events;



public record ResetPasswordRequestEvent(
        String sagaId,
        String email,
        String templateName,
        String token) {
}
