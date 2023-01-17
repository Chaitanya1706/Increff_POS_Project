package com.increff.pos.dto;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.DailyReportData;
import com.increff.pos.model.DailyReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportDto {

    @Autowired
    private DailyReportService service;

    @Autowired
    private OrderService oService;
    @Autowired
    private OrderItemService oiService;

//    public List<DailyReportData> getFilteredReport(DailyReportForm f) throws ApiException {
//
//        String startDate = f.getStartDate()+"00:00:00";
//        String endDate = f.getEndDate()+"23:59:59";
//
//        TreeMap<String,List<Integer>> map = oService.getFilteredDateWiseOrders(startDate,endDate);
//
//        List<DailyReportData> list2 = new ArrayList<DailyReportData>();
//
//        for (Map.Entry<String,List<Integer>> e : map.entrySet()){
//            List<Integer> id = e.getValue();
//            Integer orderCount = 0;
//            Integer qty = 0;
//            Integer itemCount = 0;
//            Double revenue = 0.0;
//            for(Integer i : id){
//                orderCount++;
//                List<OrderItemPojo> op = oiService.getByOrderId(i);
//                for(OrderItemPojo o : op){
//                    itemCount++;
//                    qty+=o.getQuantity();
//                    revenue += o.getSellingPrice()*o.getQuantity();
//                }
//
//            }
//            DailyReportData d = new DailyReportData();
//            d.setDate(e.getKey());
//            d.setTotalItem(itemCount);
//            d.setTotalOrder(orderCount);
//            d.setTotalRevenue(revenue);
//            list2.add(d);
//        }
//        return list2;
//    }
    public List<DailyReportData> getAll() throws ApiException {
        TreeMap<String,List<Integer>> map = oService.getDateWiseOrders();

        List<DailyReportData> list2 = new ArrayList<DailyReportData>();

        for (Map.Entry<String,List<Integer>> e : map.entrySet()){
            List<Integer> id = e.getValue();
            Integer orderCount = 0;
            Integer qty = 0;
            Integer itemCount = 0;
            Double revenue = 0.0;
            for(Integer i : id){
                orderCount++;
                List<OrderItemPojo> op = oiService.getByOrderId(i);
                for(OrderItemPojo o : op){
                    itemCount++;
                    qty+=o.getQuantity();
                    revenue += o.getSellingPrice()*o.getQuantity();
                }

            }
            DailyReportData d = new DailyReportData();
            d.setDate(e.getKey());
            d.setTotalItem(itemCount);
            d.setTotalOrder(orderCount);
            d.setTotalRevenue(revenue);
            list2.add(d);
        }
        return list2;
    }

    public List<DailyReportData> getFilteredReport(DailyReportForm f) throws ApiException {

        List<DailyReportData> list1 = getAll();
        List<DailyReportData> list2 = new ArrayList<>();

        for(DailyReportData l : list1){
            if(f.getStartDate().compareTo(l.getDate())<=0 && f.getEndDate().compareTo(l.getDate())>=0){
                list2.add(l);
            }
        }
        return list2;
    }

}
