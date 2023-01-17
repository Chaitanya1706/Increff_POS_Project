package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class DailyReportService {

    @Autowired
    private OrderDao dao;


    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }


}
