package com.example.gestionAcueducto.users;



public record SendPasswordResetCreated(String email,
                                       String templateName,
                                       String token) {
}
