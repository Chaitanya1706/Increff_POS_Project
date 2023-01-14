package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.calendar.LocalGregorianCalendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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


    public OrderPojo getCheck(int id) throws ApiException {
        OrderPojo b = dao.select(id);
        if (b == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return b;
    }

}
