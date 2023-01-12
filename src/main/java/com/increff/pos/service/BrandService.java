package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao dao;


    public void add(BrandPojo b) throws ApiException {
        normalize(b);
        if(StringUtil.isEmpty(b.getBrand())) {
            throw new ApiException("brand name cannot be empty");
        }
        BrandPojo bp = dao.getCategoryId(b.getBrand(),b.getCategory());
        if(bp!=null){
            throw new ApiException(("this brand and category already exist"));
        }
        else {
            dao.insert(b);
        }
    }


    public void delete(int id) {
        dao.delete(id);
    }


    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<BrandPojo> getCategory(String b) throws ApiException {

        if(StringUtil.isEmpty(b)){
            throw new ApiException("brand name cannot be empty");
        }
        return dao.getCategory(b);
    }

    public Integer getCategoryId(String brand, String category) {


        return dao.getCategoryId(brand,category).getId();
    }




    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }


    public void update(int id, BrandPojo b) throws ApiException {
        normalize(b);
        BrandPojo bp = dao.getCategoryId(b.getBrand(),b.getCategory());
        if(bp!=null){
            throw new ApiException(("this brand and category already exist"));
        }
        BrandPojo ex = getCheck(id);
        ex.setBrand(b.getBrand());
        ex.setCategory(b.getCategory());
        dao.update(ex);
    }


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
