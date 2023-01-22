package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.PdfData;
import com.increff.pos.model.PdfListData;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfDto {

    @Autowired
    private OrderItemService oiService;

    @Autowired
    private OrderService oService;

    @Autowired
    private ProductService pService;

    @Autowired
    private InventoryService iService;

    public PdfData get(int id) throws ApiException {

        List<OrderItemPojo> l = oiService.getByOrderId(id);
        if(l.size()==0){
            throw new ApiException("No items to place in this order");
        }
        PdfData d = new PdfData();
        d = convert(l,d);
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = LocalDateTime.now().format(date);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = LocalDateTime.now().format(time);
        d.setInvoiceTime(formattedTime);
        d.setInvoiceDate(formattedDate);
        d.setOrderId(id);
        OrderPojo op = oService.get(id);

        op.setStatus(true);
        oService.update(id,op);
        return d;
    }

    private PdfData convert(List<OrderItemPojo> l, PdfData pd) throws ApiException {
        List<PdfListData> list = new ArrayList<>();
        Double total = 0.0;
        Integer c = 0;
        for(OrderItemPojo op : l){
            c++;
            PdfListData d = new PdfListData();
            ProductPojo pp = pService.get(op.getProductId());
            InventoryPojo ip = iService.getFromProductId(op.getProductId());
            if(ip.getQuantity()<op.getQuantity()){
                throw new ApiException("Only " + ip.getQuantity() + " pieces left in inventory");
            }
            d.setSno(c);
            d.setBarcode(pp.getBarcode());
            d.setProduct(pp.getName());
            d.setQuantity(op.getQuantity());
            d.setUnitPrice(op.getSellingPrice());
            Double v = op.getQuantity()*op.getSellingPrice();
            total += v;
            d.setAmount(v);
            list.add(d);
            ip.setQuantity(ip.getQuantity()-op.getQuantity());
            iService.update(ip.getId(),ip);
        }
        pd.setTotal(total);
        pd.setItemList(list);
        return pd;
    }


}
