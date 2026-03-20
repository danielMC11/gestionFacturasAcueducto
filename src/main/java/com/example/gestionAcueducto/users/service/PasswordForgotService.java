package com.example.gestionAcueducto.users.service;

import com.example.gestionAcueducto.users.dto.PasswordForgotRequest;
import com.example.gestionAcueducto.users.dto.SimpleMessageDTO;

public interface PasswordForgotService {

    SimpleMessageDTO processForgotPassword(PasswordForgotRequest passwordForgotRequest);
}
