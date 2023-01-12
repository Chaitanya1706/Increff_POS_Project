package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer brand_category;

    @Column(unique = true)
    private String barcode;

    private String name;

    //todo -> naming strategy

    // todo -> make barcode unique
    // todo -> not null/ empty
    private Double mrp;


}
