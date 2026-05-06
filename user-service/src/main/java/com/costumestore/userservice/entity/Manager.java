package com.costumestore.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblManager")
@DiscriminatorValue("MANAGER")
@PrimaryKeyJoinColumn(name = "UserId")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends Staff {

    @Column(name = "ManagerCode", length = 50, unique = true)
    private String managerCode;

    @Column(name = "Title", length = 100)
    private String title;
}
