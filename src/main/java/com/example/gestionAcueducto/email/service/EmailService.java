package com.example.gestionAcueducto.email.service;

public interface EmailService {

    void sendResetPasswordEmail(String email, String templateName, String token);

}
