package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;
import org.hibernate.secure.spi.IntegrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = ApiException.class)
public class SalesReportService {

    @Autowired
    private ProductService pService;
    @Autowired
    private OrderItemService oiService;

    @Autowired
    private BrandService bService;

    @Autowired
    private OrderService oService;

    public List<SalesReportData> getAll() throws ApiException {
        List<OrderPojo> op = oService.getAll();
        return getFilteredSalesReportDateWise(op,"all","all");
//        HashMap<String,List<String>> map = bService.getBrandWiseCategory();
//
//        List<SalesReportData> list2 = new ArrayList<SalesReportData>();
//
//        for (Map.Entry<String,List<String>> e : map.entrySet()){
//            List<String> categories = e.getValue();
//
//            for(String c : categories){
//                Integer qty = 0;
//                Double revenue = 0.0;
//
//                Integer id = bService.getCategoryId(e.getKey(),c);
//                List<ProductPojo> pp = pService.getProductFromBrandId(id);
//                for(ProductPojo p : pp){
//                    List<OrderItemPojo> op = oiService.getFromProductId(p.getId());
//                    for(OrderItemPojo o : op){
//                        qty+=o.getQuantity();
//                        revenue += o.getSellingPrice()*o.getQuantity();
//                    }
//
//                }
//
//                SalesReportData d = new SalesReportData();
//                d.setBrand(e.getKey());
//                d.setCategory(c);
//                d.setQuantity(qty);
//                d.setRevenue(revenue);
////                System.out.println(d.getBrand()+"   ");
//                list2.add(d);
//
//            }
//
//        }
//        return list2;
    }

    public List<SalesReportData> getFilteredSalesReportDateWise(List<OrderPojo> op, String brand, String category) throws ApiException {
        HashMap<Integer,SalesReportData> map = new HashMap<>();

        for (OrderPojo o : op) {
            List<OrderItemPojo> oip = oiService.getByOrderId(o.getId());
            for (OrderItemPojo oi : oip) {
                ProductPojo pp = pService.get(oi.getProductId());
                BrandPojo bp = bService.get(pp.getBrand_category());
                if (!map.containsKey(bp.getId())) {
                    map.put(bp.getId(), new SalesReportData());
                }
                SalesReportData d = map.get(bp.getId());
                d.setQuantity(d.getQuantity() + oi.getQuantity());
                d.setRevenue(d.getRevenue() + (oi.getQuantity() * oi.getSellingPrice()));
            }
        }


        List<SalesReportData> output = new ArrayList<>();

        for (Map.Entry<Integer,SalesReportData> e : map.entrySet()){

            BrandPojo bp = bService.get(e.getKey());
            if((Objects.equals(brand,bp.getBrand()) || Objects.equals(brand,"all")) && (Objects.equals(category,bp.getCategory()) || Objects.equals(category,"all"))){
                SalesReportData d = e.getValue();
                d.setBrand(bp.getBrand());
                d.setCategory(bp.getCategory());
                output.add(d);
            }
        }


        return output;
    }


}
