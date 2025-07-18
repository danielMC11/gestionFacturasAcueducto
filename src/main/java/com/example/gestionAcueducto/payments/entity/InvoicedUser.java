package com.example.gestionAcueducto.payments.entity;


import com.example.gestionAcueducto.enums.InvoiceStatus;
import com.example.gestionAcueducto.invoices.entity.Invoice;
import com.example.gestionAcueducto.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoiced_users")
@Entity
public class InvoicedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

}
