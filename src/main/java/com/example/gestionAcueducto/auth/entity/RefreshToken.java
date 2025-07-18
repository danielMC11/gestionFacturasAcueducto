package com.example.gestionAcueducto.auth.entity;

import com.example.gestionAcueducto.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O usa GenerationType.AUTO dependiendo de tu DB
    private Long id;

    @Column(unique = true) // Asegura que el token siga siendo único
    private String token;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private Date expirationDate;


}

