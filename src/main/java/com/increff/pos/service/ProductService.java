package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao dao;


    public void add(ProductPojo p) throws ApiException {
        normalize(p);
        if(StringUtil.isEmpty(p.getName())) {
            throw new ApiException("product name cannot be empty");
        }
        dao.insert(p);
    }


    public void delete(int id) {
        dao.delete(id);
    }


    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }


    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setMrp(p.getMrp());
        ex.setBarcode(p.getBarcode());
        ex.setBrand_category(p.getBrand_category());
        dao.update(ex);
    }


    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("product with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()).trim());
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()).trim());
    }
}
