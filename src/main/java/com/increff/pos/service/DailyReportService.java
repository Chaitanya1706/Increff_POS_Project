package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.model.DailyReportData;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional(rollbackFor = ApiException.class)
public class DailyReportService {

    @Autowired
    private OrderDao dao;

    @Autowired
    private OrderItemService oiService;

    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }

    public List<DailyReportData> setDetails(TreeMap<String,List<Integer>> map){
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

}
