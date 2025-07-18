package com.example.gestionAcueducto.invoices.entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "negotiation_types")
@Entity
public class NegotiationType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Short id;
}
