package com.example.gestionAcueducto.service;

public interface EmailService {

    void sendResetPasswordEmail(String email, String token);

}
