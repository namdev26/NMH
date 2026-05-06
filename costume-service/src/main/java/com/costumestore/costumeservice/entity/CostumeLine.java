package com.costumestore.costumeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tblCostumeLine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostumeLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false, length = 150)
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "costumeLine", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Costume> costumes = new ArrayList<>();
}
