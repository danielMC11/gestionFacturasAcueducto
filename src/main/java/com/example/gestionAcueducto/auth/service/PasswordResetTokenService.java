package com.example.gestionAcueducto.auth.service;

import com.example.gestionAcueducto.auth.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;

public interface PasswordResetTokenService {

    String createTokenForUser(User user, int minutes);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    boolean isTokenExpired(PasswordResetToken passwordResetToken);


    void deleteToken(PasswordResetToken passwordResetToken);

}
