package com.example.gestionAcueducto.invoices.entity;


import com.example.gestionAcueducto.invoices.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE invoices SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "invoices")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer consecutiveNumber;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private BigDecimal paymentTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    private List<InvoiceItem> invoiceItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    private List<Payment> payments;

    private boolean deleted = Boolean.FALSE;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
