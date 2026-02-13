package com.example.gestionAcueducto.invoices.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE invoice_issuers SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "issuers")
@Entity
public class Issuer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String businessName;
    @Column(nullable = false, length = 10, unique = true)
    private String nit;
    @Column(nullable = false, length = 254)
    private String address;
    @Column(nullable = false, unique = true, length = 254)
    private String email;
    @Column(nullable = false, length = 15)
    private String phoneNumber;

    private boolean deleted = Boolean.FALSE;

}
