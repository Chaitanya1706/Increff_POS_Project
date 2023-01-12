package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEditForm{

	private String barcode;
	private String name;

	private double mrp;

	private Integer id;

}
