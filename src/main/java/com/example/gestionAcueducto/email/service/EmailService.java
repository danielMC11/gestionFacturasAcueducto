package com.example.gestionAcueducto.email.service;

import java.util.UUID;

public interface EmailService {

    void sendResetPasswordEmail(UUID sagaId, String email, String templateName, String token);

}
