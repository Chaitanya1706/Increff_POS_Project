package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.OrderForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;

    public void add() throws ApiException {
        service.add();
    }

    public OrderData get(int id) throws ApiException {
        OrderPojo p = service.get(id);
        return convert(p);
    }

    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> list = service.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo o : list) {
            list2.add(convert(o));
        }
        return list2;
    }

    private OrderData convert(OrderPojo o)throws ApiException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = o.getDate().format(dateTimeFormatter);
        OrderData d = new OrderData();
        d.setId(o.getId());
        d.setDate(formattedDateTime);
        d.setStatus(o.getStatus());
        return d;
    }

}
