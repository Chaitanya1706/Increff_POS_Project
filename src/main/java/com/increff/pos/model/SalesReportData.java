package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReportData {

	private Integer id;

	private String brand;
	private String category;
	private Integer quantity;
	private Double revenue;


	public SalesReportData(){
		this.setQuantity(0);
		this.setRevenue(0.0);
	}
}
