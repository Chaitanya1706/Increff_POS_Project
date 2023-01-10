package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.EmployeeDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo b) throws ApiException {
        normalize(b);
        if(StringUtil.isEmpty(b.getBrand())) {
            throw new ApiException("brand name cannot be empty");
        }
        dao.insert(b);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo b) throws ApiException {
        normalize(b);
        BrandPojo ex = getCheck(id);
        ex.setBrand(b.getBrand());
        ex.setCategory(b.getCategory());
        dao.update(ex);
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo b = dao.select(id);
        if (b == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return b;
    }

    protected static void normalize(BrandPojo b) {
        b.setBrand(StringUtil.toLowerCase(b.getBrand()));
    }
}
