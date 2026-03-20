package com.example.gestionAcueducto.email.service;

import com.example.gestionAcueducto.events.ResetPasswordRequestEvent;

public interface EmailMessageService {

    void onResetPasswordRequest(ResetPasswordRequestEvent resetPasswordRequestEvent);

}
