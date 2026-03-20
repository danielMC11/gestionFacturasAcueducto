package com.example.gestionAcueducto.users.service;

import com.example.gestionAcueducto.events.ResetPasswordRequestEvent;

public interface UserMessageService {

    void sendResetPasswordEmail(ResetPasswordRequestEvent event);


}
