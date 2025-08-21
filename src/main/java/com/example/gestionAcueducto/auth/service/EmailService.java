package com.example.gestionAcueducto.auth.service;

public interface EmailService {

    void sendResetPasswordEmail(String email, String templateName, String token);

}
