package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService pService;
    public void add(InventoryForm form) throws ApiException {

        InventoryPojo b = convert(form);

        service.add(b);
    }



    public InventoryData get(int id) throws ApiException {
        InventoryPojo p = service.get(id);
        return convert(p);
    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = service.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }


    public void update(int id, InventoryForm f) throws ApiException {
        InventoryPojo p = convert(f);
        service.update(id, p);
    }


    private InventoryData convert(InventoryPojo i) throws ApiException {
        InventoryData d = new InventoryData();
        d.setQuantity(i.getQuantity());
        d.setBarcode(pService.get(i.getProductId()).getBarcode());
        d.setProductName(i.getProductName());
        d.setId(i.getId());
        return d;
    }

    private InventoryPojo convert(InventoryForm f) throws ApiException {
        InventoryPojo i = new InventoryPojo();
        int id = pService.getIdFromBarcode(f.getBarcode());
        ProductPojo p = pService.get(id);
        i.setProductId(p.getId());
        i.setProductName(p.getName());
        i.setQuantity(f.getQuantity());
        return i;
    }
}
