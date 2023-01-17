package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class DailyReportData {

	private Integer id;

	private String date;
	private Integer totalOrder;
	private Integer totalItem;

	private Double totalRevenue;

}
