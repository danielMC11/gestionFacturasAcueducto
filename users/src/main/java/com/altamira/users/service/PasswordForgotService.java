package com.altamira.users.service;

import com.altamira.users.dto.PasswordForgotRequest;

public interface PasswordForgotService {

    String processForgotPassword(PasswordForgotRequest passwordForgotRequest);
}
