package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Autowired
    private ProductService pService;
    public void add(InventoryPojo i) throws ApiException {

        if(i.getProductId()==null) {
            throw new ApiException("inventory id cannot be empty");
        }
        InventoryPojo ip = dao.getFromId(i.getProductId());
        if(ip==null){
            dao.insert(i);
        }
        else{
            ip.setQuantity(ip.getQuantity()+i.getQuantity());
            dao.update(ip);
        }

    }

    public InventoryPojo getFromProductId(Integer pid){
        return dao.getFromId(pid);
    }

    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }


    public void update(int id, InventoryPojo i) throws ApiException {

        InventoryPojo ex = getCheck(id);
        ex.setQuantity(i.getQuantity());
        dao.update(ex);
    }


    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo i = dao.select(id);
        if (i == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return i;
    }



}
