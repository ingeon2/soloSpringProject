package com.soloProject.server.domain.balance.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class Balance {
    @Id
    private Long balance_id = 1L; //오직 하나

    private int amount;

}