package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {

    @Autowired
    private ProductService service;

    @Autowired
    private BrandService brandService;
    public void add(ProductForm form) throws ApiException {
        ProductPojo p = convert(form);
        service.add(p);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = service.get(id);
        return convert(p);
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, ProductEditForm f) throws ApiException {
        ProductPojo p = convertEditForm(f);
//        System.out.println(p);
        service.update(id, p);
    }

    private ProductData convert(ProductPojo p)throws ApiException {
        ProductData d = new ProductData();
        BrandPojo bp = brandService.get(p.getBrand_category());
        d.setBrand(bp.getBrand());
        d.setBrandId(bp.getId());
        d.setCategory(bp.getCategory());
        d.setName(p.getName());
        d.setBarcode(p.getBarcode());
        d.setMrp(p.getMrp());
        d.setId(p.getId());
        return d;
    }

    private ProductPojo convert(ProductForm f) {
        ProductPojo p = new ProductPojo();
        p.setBrand_category(brandService.getCategoryId(f.getBrand(),f.getCategory()));
        p.setBarcode(f.getBarcode());
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        return p;
    }

    private ProductPojo convertEditForm(ProductEditForm f) {
        ProductPojo p = new ProductPojo();
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        p.setId(f.getId());
        p.setBarcode(f.getBarcode());
        return p;
    }
}
