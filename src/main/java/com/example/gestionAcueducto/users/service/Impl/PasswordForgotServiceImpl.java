package com.example.gestionAcueducto.users.service.Impl;

import com.example.gestionAcueducto.users.dto.PasswordForgotRequest;
import com.example.gestionAcueducto.users.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.users.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
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



    public SimpleMessageDTO processForgotPassword(@Valid @RequestBody PasswordForgotRequest passwordForgotRequest) {

        User user = userService.findByEmail(passwordForgotRequest.email());

        PasswordResetToken passwordResetToken;
        passwordResetToken = passwordResetTokenService.findByUser(user);

        if(passwordResetToken != null && passwordResetTokenService.isTokenExpired(passwordResetToken)){
            passwordResetTokenService.deleteToken(passwordResetToken);
        }


        passwordResetTokenService.createPasswordResetToken(user, 5);


        return new SimpleMessageDTO("Se ha generado un nuevo correo de recuperación exitosamente!");

    }
}
