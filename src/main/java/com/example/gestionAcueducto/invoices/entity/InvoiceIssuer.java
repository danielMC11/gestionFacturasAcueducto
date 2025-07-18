package com.example.gestionAcueducto.invoices.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.example.gestionAcueducto.users.entity.User;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE invoice_issuers SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "invoice_issuers")
@Entity
public class InvoiceIssuer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String businessName;
    @Column(nullable = false, length = 10, unique = true)
    private String nit;
    @Column(nullable = false, length = 254)
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;
    @Column(nullable = false, unique = true, length = 254)
    private String email;
    @Column(nullable = false, length = 15)
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accounting_regime_id")
    private AccountingRegime accountingRegime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsibility_type_id")
    private ResponsibilityType responsibilityType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "economic_activity_id")
    private EconomicActivity economicActivity;
    @ManyToOne
    @JoinColumn(name = "operation_type_id")
    private OperationType operationType;
    @ManyToOne
    @JoinColumn(name = "negotiation_type_id")
    private NegotiationType negotiationType;

    private boolean deleted = Boolean.FALSE;

}
