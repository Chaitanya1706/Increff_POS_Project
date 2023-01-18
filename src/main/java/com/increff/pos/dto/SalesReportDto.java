package com.increff.pos.dto;

import com.increff.pos.model.DailyReportData;
import com.increff.pos.model.DailyReportForm;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SalesReportDto {

    @Autowired
    private SalesReportService service;

    @Autowired
    private OrderService oService;

    public List<SalesReportData> getAll() throws ApiException {
        return service.getAll();
    }

    public List<SalesReportData> getFilteredSalesReportDateWise(SalesReportForm f) throws ApiException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDate = f.getStartDate()+" 00:00:00";
        String endDate = f.getEndDate()+" 23:59:59";
        LocalDateTime sdate = LocalDateTime.parse(startDate, dateTimeFormatter);
        LocalDateTime edate = LocalDateTime.parse(endDate, dateTimeFormatter);
        List<OrderPojo> op= oService.getFilteredOrder(sdate,edate);

        return service.getFilteredSalesReportDateWise(op,f.getBrand(),f.getCategory());
    }



}
