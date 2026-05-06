package com.costumestore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tblStaff")
@DiscriminatorValue("STAFF")
@PrimaryKeyJoinColumn(name = "UserId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends User {

    @Column(name = "Salary", precision = 15, scale = 2)
    private BigDecimal salary;

    @Column(name = "Position", length = 100)
    private String position;
}
