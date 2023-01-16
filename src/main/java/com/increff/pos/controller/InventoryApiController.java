package com.increff.pos.controller;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryApiController {

    @Autowired
    private InventoryDto idto;

    @ApiOperation(value = "Adds a Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        idto.add(form);
    }

    @ApiOperation(value = "Gets list of all inventories")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {

        return idto.getAll();
    }

    @ApiOperation(value = "Gets inventory report")
    @RequestMapping(path = "/api/inventory/report", method = RequestMethod.GET)
    public List<InventoryReportData> getInventoryReport() throws ApiException {

        return idto.getInventoryReport();
    }
    @ApiOperation(value = "Gets a inventory by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        return idto.get(id);
    }


    @ApiOperation(value = "Updates a Inventory")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        idto.update(id,f);
    }




}
