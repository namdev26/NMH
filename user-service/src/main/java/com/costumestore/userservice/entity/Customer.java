package com.costumestore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblCustomer")
@DiscriminatorValue("CUSTOMER")
@PrimaryKeyJoinColumn(name = "UserId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    @Column(name = "RewardPoint")
    private Integer rewardPoint;

    @Column(name = "CustomerRanking", length = 50)
    private String customerRanking;
}
