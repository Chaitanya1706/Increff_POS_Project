package com.increff.pos.dto;

import com.increff.pos.model.DailyReportData;
import com.increff.pos.model.DailyReportForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DailyReportDto {

    @Autowired
    private DailyReportService service;

    @Autowired
    private OrderService oService;
    @Autowired
    private OrderItemService oiService;

    public List<DailyReportData> getFilteredReport(DailyReportForm f) throws ApiException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDate = f.getStartDate()+" 00:00:00";
        String endDate = f.getEndDate()+" 23:59:59";
        LocalDateTime sdate = LocalDateTime.parse(startDate, dateTimeFormatter);
        LocalDateTime edate = LocalDateTime.parse(endDate, dateTimeFormatter);
        TreeMap<String,List<Integer>> map = oService.getFilteredDateWiseOrders(sdate,edate);
        return setDetails(map);

    }
    public List<DailyReportData> getAll() throws ApiException {
        TreeMap<String,List<Integer>> map = oService.getDateWiseOrders();

        return setDetails(map);
    }

    public List<DailyReportData> setDetails(TreeMap<String,List<Integer>> map){
        return service.setDetails(map);
    }


}
