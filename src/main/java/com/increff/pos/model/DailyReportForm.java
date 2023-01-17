package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DailyReportForm {

	private Integer id;

	private String startDate;
	private String endDate;

}
