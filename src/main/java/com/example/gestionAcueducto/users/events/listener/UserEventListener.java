package com.example.gestionAcueducto.users.events.listener;

import com.example.gestionAcueducto.auth.service.EmailService;
import com.example.gestionAcueducto.auth.service.PasswordResetTokenService;
import com.example.gestionAcueducto.users.events.UserCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserEventListener {

    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;

    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        String token = passwordResetTokenService.createTokenForUser(event.getUser(), 43200);
        emailService.sendResetPasswordEmail(event.getUser().getEmail(), "set-password-email", token);
    }
}