package com.costumestore.costumeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblCostume")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Costume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Category", length = 100)
    private String category;

    @Column(name = "Size", length = 20)
    private String size;

    @Column(name = "Color", length = 50)
    private String color;

    @Column(name = "Material", length = 100)
    private String material;

    @Column(name = "SellPrice", nullable = false, precision = 15, scale = 2)
    private BigDecimal sellPrice;

    @Column(name = "StockQuantity", nullable = false)
    @Builder.Default
    private Integer stockQuantity = 0;

    @Column(name = "SupplierId")
    private Long supplierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CostumeLineId", nullable = false)
    private CostumeLine costumeLine;

    @OneToMany(mappedBy = "costume", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CostumeDetail> costumeDetails = new ArrayList<>();

    @OneToMany(mappedBy = "costume", fetch = FetchType.LAZY)
    @Builder.Default
    private List<BillCostumeDetail> billCostumeDetails = new ArrayList<>();
}
