package com.increff.pos.dto;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemEditForm;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDto {

    @Autowired
    private OrderItemService service;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;
    public void add(OrderItemForm form, Integer id) throws ApiException {
        OrderItemPojo p = convert(form,id);
        service.add(p);
    }

    public void delete(int id) throws ApiException {
        OrderItemPojo op = service.get(id);
        service.delete(id);
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo p = service.get(id);
        return convert(p);
    }

    public List<OrderItemData> getAll() throws ApiException {
        List<OrderItemPojo> list = service.getAll();
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public List<OrderItemData> getByOrderId(Integer id) throws ApiException {
        List<OrderItemPojo> list = service.getByOrderId(id);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, OrderItemEditForm f) throws ApiException {
        OrderItemPojo p = convertEditForm(f,id);
//        System.out.println(p);
        service.update(id, p);
    }

    private OrderItemData convert(OrderItemPojo p)throws ApiException {
        OrderItemData d = new OrderItemData();
        ProductPojo pp = productService.get(p.getProductId());
        d.setProductName(pp.getName());
        d.setOrderId(p.getOrderId());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        d.setId(p.getId());
        return d;
    }

    private OrderItemPojo convert(OrderItemForm f, Integer id) throws ApiException {

        OrderItemPojo p = new OrderItemPojo();
        int pid = productService.getIdFromBarcode(f.getBarcode());
        ProductPojo pp = productService.get(pid);
        InventoryPojo ip = inventoryService.getFromProductId(pid);
        if(f.getQuantity()<0||f.getSellingPrice()<0){
            throw new ApiException("negative input not allowed");
        }
        if(f.getSellingPrice()>pp.getMrp()){
            throw new ApiException("Selling Price can not be greater than MRP");
        }
        else if(f.getQuantity()>ip.getQuantity()){
            throw new ApiException("Only " + ip.getQuantity() + " pieces left in inventory");
        }
        p.setProductId(pid);
        p.setOrderId(id);
        p.setQuantity(f.getQuantity());
        p.setSellingPrice(f.getSellingPrice());

        return p;
    }

    private OrderItemPojo convertEditForm(OrderItemEditForm f, Integer id) throws ApiException {
        OrderItemPojo p = new OrderItemPojo();
        InventoryPojo ip = inventoryService.getFromProductId(service.get(id).getProductId());
        int q = ip.getQuantity()+service.get(id).getQuantity();
        ProductPojo pp = productService.get(service.get(id).getProductId());
        if(f.getQuantity()<0||f.getSellingPrice()<0){
            throw new ApiException("negative input not allowed");
        }
        if(f.getSellingPrice()>pp.getMrp()){
            throw new ApiException("Selling Price can not be greater than MRP");
        }
        else if(f.getQuantity()>q){
            throw new ApiException("Only " + ip.getQuantity() + " pieces left in inventory");
        }

//        ip.setQuantity(q-f.getQuantity());
//        inventoryService.update(ip.getId(),ip);
        p.setQuantity(f.getQuantity());
        p.setSellingPrice(f.getSellingPrice());
        return p;
    }
}
