package com.increff.pos.dto;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BrandDto {

    @Autowired
    private BrandService service;
    public void add(BrandForm form) throws ApiException {
        BrandPojo b = convert(form);
        service.add(b);
    }

    public BrandData get(int id) throws ApiException {
        BrandPojo p = service.get(id);
        return convert(p);
    }

    public List<BrandData> getAll() {
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public List<String> getAllUniqueBrands() {
        List<BrandPojo> list = service.getAll();
        Set s = new HashSet();

        for (BrandPojo p : list) {
            s.add(p.getBrand());
        }

        List<String> l = new ArrayList<>(s);
        return l;
    }
    public void update(int id, BrandForm f) throws ApiException {
        BrandPojo p = convert(f);
        service.update(id, p);
    }

    public List<String> getCategory(String categ) throws ApiException {
        List<BrandPojo> list = service.getCategory(categ);
        List<String> list2 = new ArrayList<String>();
        for(BrandPojo b : list){
            list2.add(b.getCategory());
        }
        return list2;
    }

    private static BrandData convert(BrandPojo b) {
        BrandData d = new BrandData();
        d.setBrand(b.getBrand());
        d.setCategory(b.getCategory());
        d.setId(b.getId());
        return d;
    }

    private static BrandPojo convert(BrandForm f) {
        BrandPojo b = new BrandPojo();
        b.setCategory(f.getCategory());
        b.setBrand(f.getBrand());
        return b;
    }
}
