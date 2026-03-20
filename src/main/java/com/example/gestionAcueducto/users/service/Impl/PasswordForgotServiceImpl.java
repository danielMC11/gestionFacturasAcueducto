package com.example.gestionAcueducto.users.service.Impl;

import com.example.gestionAcueducto.events.ResetPasswordRequestEvent;
import com.example.gestionAcueducto.users.dto.PasswordForgotRequest;
import com.example.gestionAcueducto.users.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.users.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.service.UserMessageService;
import com.example.gestionAcueducto.users.service.PasswordForgotService;
import com.example.gestionAcueducto.users.service.PasswordResetTokenService;
import com.example.gestionAcueducto.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class PasswordForgotServiceImpl implements PasswordForgotService {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserMessageService userMessageService;



    public SimpleMessageDTO processForgotPassword(@Valid @RequestBody PasswordForgotRequest passwordForgotRequest) {

        User user = userService.findByEmail(passwordForgotRequest.email());

        PasswordResetToken passwordResetToken;
        passwordResetToken = passwordResetTokenService.findByUser(user);

        if(passwordResetToken != null){

            if(!passwordResetTokenService.isTokenExpired(passwordResetToken)){

                ResetPasswordRequestEvent resetPasswordRequestEvent = new ResetPasswordRequestEvent( passwordResetToken.getSagaId(),
                        user.getEmail(), "reset-password-email", passwordResetToken.getToken());

                userMessageService.publishResetPasswordEvent(resetPasswordRequestEvent);

                return new SimpleMessageDTO("Se ha reenviado el correo exitosamente!");
            }
            passwordResetTokenService.deleteToken(passwordResetToken);
        }

        passwordResetToken = passwordResetTokenService.createPasswordResetToken(user, 5);


        ResetPasswordRequestEvent resetPasswordRequestEvent = new ResetPasswordRequestEvent( passwordResetToken.getSagaId(),
                user.getEmail(), "reset-password-email", passwordResetToken.getToken());

        userMessageService.publishResetPasswordEvent(resetPasswordRequestEvent);

        return new SimpleMessageDTO("Se ha generado un nuevo correo de recuperación exitosamente!");

    }
}
