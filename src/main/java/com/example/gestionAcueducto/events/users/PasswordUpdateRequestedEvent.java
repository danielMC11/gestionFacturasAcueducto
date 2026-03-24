package com.example.gestionAcueducto.events.users;

import com.example.gestionAcueducto.events.BaseEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordUpdateRequestedEvent extends BaseEvent {

    private final String email;
    private final String token;
    private final String template;

    public PasswordUpdateRequestedEvent(UUID sagaId, String email, String template, String token) {
        super(sagaId);
        this.email = email;
        this.template = template;
        this.token = token;
    }


    public String getEmail() {return this.email;}
    public String getToken() {return this.token;}
    public String getTemplate() {return this.template;}


}
