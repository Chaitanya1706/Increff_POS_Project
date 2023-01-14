package com.increff.pos.controller;
import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto odto;

    @ApiOperation(value = "Adds a Order")
    @RequestMapping(path = "/api/order/create", method = RequestMethod.POST)
    public void add() throws ApiException {
        odto.add();
    }

    @ApiOperation(value = "Gets an order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int id) throws ApiException {
        return odto.get(id);
    }

    @ApiOperation(value = "Gets list of all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {

        return odto.getAll();
    }
 
    //todo -> naming convention plural


}
