package com.altamira.common.events.users;

import com.altamira.common.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PasswordUpdateRequestedEvent extends BaseEvent {

    private String email;
    private String token;
    private String template;


    public PasswordUpdateRequestedEvent(UUID sagaId, String email, String template, String token) {
        super(sagaId);
        this.email = email;
        this.template = template;
        this.token = token;
    }

}
