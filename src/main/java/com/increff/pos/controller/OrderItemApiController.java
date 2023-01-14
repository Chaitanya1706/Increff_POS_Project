package com.increff.pos.controller;
import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemEditForm;
import com.increff.pos.model.ProductEditForm;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderItemApiController {

    @Autowired
    private OrderItemDto oidto;

    @ApiOperation(value = "Adds an order item")
    @RequestMapping(path = "/api/order-item/{id}", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form, @PathVariable Integer id) throws ApiException {
        oidto.add(form,id);
    }


    @ApiOperation(value = "Deletes an order-item")
    @RequestMapping(path = "/api/order-item/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) throws ApiException {
        oidto.delete(id);
    }

    @ApiOperation(value = "Gets an order-item by ID")
    @RequestMapping(path = "/api/order-item/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException {
        return oidto.get(id);
    }

    @ApiOperation(value = "Gets list of all order-items")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.GET)
    public List<OrderItemData> getAll() throws ApiException {

        return oidto.getAll();
    }

    @ApiOperation(value = "Gets list of order-items by order-id")
    @RequestMapping(path = "/api/order-item/order/{orderId}", method = RequestMethod.GET)
    public List<OrderItemData> getByOrderId(@PathVariable int orderId) throws ApiException {

        return oidto.getByOrderId(orderId);
    }
    @ApiOperation(value = "Updates an order-item")
    @RequestMapping(path = "/api/order-item/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemEditForm f) throws ApiException {
        oidto.update(id,f);
    }

    //todo -> naming convention plural


}
