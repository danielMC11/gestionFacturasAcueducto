package com.example.gestionAcueducto.users.service;

import com.example.gestionAcueducto.users.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.enums.EmailStatus;

import java.util.UUID;

public interface PasswordResetTokenService {

    void createPasswordResetToken(User user, int minutes);

    void updatePasswordResetTokenStatus(UUID sagaId, EmailStatus emailStatus);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    boolean isTokenExpired(PasswordResetToken passwordResetToken);


    void deleteToken(PasswordResetToken passwordResetToken);

}
