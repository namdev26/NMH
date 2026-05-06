package com.costumestore.costumeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tblBillCostumeDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillCostumeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BillId", nullable = false)
    private Long billId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CostumeId", nullable = false)
    private Costume costume;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;
}
