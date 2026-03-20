package com.example.gestionAcueducto.users.service;

import com.example.gestionAcueducto.users.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;

public interface PasswordResetTokenService {

    String createTokenForUser(User user, int minutes);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    boolean isTokenExpired(PasswordResetToken passwordResetToken);


    void deleteToken(PasswordResetToken passwordResetToken);

}
