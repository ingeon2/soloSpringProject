package com.soloProject.server.domain.inventory.entity;


import com.soloProject.server.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int buyPrice;

    @Column(nullable = false)
    private int sellPrice;

}
