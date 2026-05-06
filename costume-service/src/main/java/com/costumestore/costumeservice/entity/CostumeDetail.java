package com.costumestore.costumeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tblCostumeDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostumeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CostumeId", nullable = false)
    private Costume costume;

    @Column(name = "ImportPrice", nullable = false, precision = 15, scale = 2)
    private BigDecimal importPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "ImportingBillId")
    private Long importingBillId;
}
