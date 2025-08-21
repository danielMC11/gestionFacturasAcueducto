package com.example.gestionAcueducto.users.entity;


import com.example.gestionAcueducto.users.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @OneToOne(mappedBy = "role")
    private User user;

}
