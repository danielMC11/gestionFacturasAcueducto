package com.altamira.users.service.Impl;

import com.altamira.users.dto.PasswordForgotRequest;
import com.altamira.users.entity.PasswordResetToken;
import com.altamira.users.entity.User;
import com.altamira.users.service.PasswordForgotService;
import com.altamira.users.service.PasswordResetTokenService;
import com.altamira.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class PasswordForgotServiceImpl implements PasswordForgotService {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;



    public String processForgotPassword(@Valid @RequestBody PasswordForgotRequest passwordForgotRequest) {

        User user = userService.findByEmail(passwordForgotRequest.email());

        PasswordResetToken passwordResetToken;
        passwordResetToken = passwordResetTokenService.findByUser(user);

        if(passwordResetToken != null){
            passwordResetTokenService.deleteToken(passwordResetToken);
        }

        return passwordResetTokenService.createPasswordResetToken(user, 5);

    }
}
