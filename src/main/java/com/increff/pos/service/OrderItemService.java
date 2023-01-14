package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;


    public void add(OrderItemPojo o) throws ApiException {

        dao.insert(o);

    }

//    public OrderItemPojo getFromBarcode(int id) {
//        return dao.getFromBarcode(id);
//    }
    public void delete(int id) {
        dao.delete(id);
    }


    public OrderItemPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<OrderItemPojo> getAll() {
        return dao.selectAll();
    }


    public List<OrderItemPojo> getByOrderId(Integer id) {
        return dao.selectByOrderId(id);
    }

    public void update(int id, OrderItemPojo b) throws ApiException {
        OrderItemPojo ex = getCheck(id);
        ex.setQuantity(b.getQuantity());
        ex.setSellingPrice(b.getSellingPrice());
        dao.update(ex);
    }


    public OrderItemPojo getCheck(int id) throws ApiException {
        OrderItemPojo b = dao.select(id);
        if (b == null) {
            throw new ApiException("OrderItem with given ID does not exit, id: " + id);
        }
        return b;
    }


}
