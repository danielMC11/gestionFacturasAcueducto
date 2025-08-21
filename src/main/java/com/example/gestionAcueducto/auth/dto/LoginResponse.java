package com.example.gestionAcueducto.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public record LoginResponse (
        String email,
        List<String> roles
){}