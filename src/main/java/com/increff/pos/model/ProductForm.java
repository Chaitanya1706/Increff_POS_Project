package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm extends BrandForm{

	private String barcode;
	private String name;

	private double mrp;

}
