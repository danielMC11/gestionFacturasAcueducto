package com.example.gestionAcueducto.events;



public record ResetPasswordRequestEvent(String email,
                                        String templateName,
                                        String token) {
}
