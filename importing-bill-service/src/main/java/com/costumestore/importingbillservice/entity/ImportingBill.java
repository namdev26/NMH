package com.costumestore.importingbillservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tblImportingBill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportingBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ManagerId", nullable = false)
    private Long managerId;

    @Column(name = "SupplierId", nullable = false)
    private Long supplierId;

    @Column(name = "ImportDate", nullable = false)
    private LocalDateTime importDate;

    @Column(name = "TotalAmount", precision = 18, scale = 2)
    private BigDecimal totalAmount;
}
