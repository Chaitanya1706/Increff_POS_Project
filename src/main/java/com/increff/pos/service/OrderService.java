package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.calendar.LocalGregorianCalendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(rollbackFor = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao dao;


    public void add() throws ApiException {
        OrderPojo op = new OrderPojo();
        op.setDate((LocalDateTime.now()));
        dao.insert(op);
    }



    public OrderPojo get(int id) throws ApiException {
        return getCheck(id);
    }


    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }

    public TreeMap<String,List<Integer>> getDateWiseOrders() throws ApiException {
        List<OrderPojo> op = dao.selectAll();
        TreeMap<String,List<Integer>> map = new TreeMap<>();

            for (OrderPojo o : op) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDateTime = o.getDate().format(dateTimeFormatter);
                if (!map.containsKey(formattedDateTime)) {
                    map.put(formattedDateTime, new ArrayList<>());
                }

                map.get(formattedDateTime).add(o.getId());

            }
            return map;

    }


    public TreeMap<String,List<Integer>> getFilteredDateWiseOrders(LocalDateTime s, LocalDateTime e) throws ApiException {
        List<OrderPojo> op = dao.selectAllFiltered(s,e);
        TreeMap<String,List<Integer>> map = new TreeMap<>();

        for (OrderPojo o : op) {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = o.getDate().format(dateTimeFormatter);
            if (!map.containsKey(formattedDateTime)) {
                map.put(formattedDateTime, new ArrayList<>());
            }

            map.get(formattedDateTime).add(o.getId());

        }
        return map;

    }

    public List<OrderPojo> getFilteredOrder(LocalDateTime s, LocalDateTime e){
        return dao.selectAllFiltered(s,e);
    }

    public OrderPojo getCheck(int id) throws ApiException {
        OrderPojo b = dao.select(id);
        if (b == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return b;
    }

}
