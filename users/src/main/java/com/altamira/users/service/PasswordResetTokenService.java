package com.altamira.users.service;

import com.altamira.users.entity.PasswordResetToken;
import com.altamira.users.entity.User;
import com.altamira.users.enums.EmailStatus;

import java.util.UUID;

public interface PasswordResetTokenService {

    String createPasswordResetToken(User user, int minutes);

    void updatePasswordResetTokenStatus(UUID sagaId, EmailStatus emailStatus);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    boolean isTokenExpired(PasswordResetToken passwordResetToken);


    void deleteToken(PasswordResetToken passwordResetToken);

}
