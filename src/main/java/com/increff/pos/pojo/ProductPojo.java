package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ProductPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int brand_category;

    private String barcode;

    private String name;

    // todo -> change to primitive data type

    //todo -> naming strategy

    // todo -> make barcode unique
    // todo -> not null/ empty
    private double mrp;


}
