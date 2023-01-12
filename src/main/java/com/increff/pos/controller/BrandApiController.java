package com.increff.pos.controller;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandDto bdto;

    @ApiOperation(value = "Adds a Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        bdto.add(form);
    }


    @ApiOperation(value = "Deletes a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) {
        bdto.delete(id);
    }

    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return bdto.get(id);
    }

    @ApiOperation(value = "Gets brand category list")
    @RequestMapping(path = "/api/brand/categ", method = RequestMethod.POST)
    public List<String> getCategory(@RequestBody String brand) throws ApiException {
        return bdto.getCategory(brand);
    }

    @ApiOperation(value = "Gets list of all brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {

        return bdto.getAll();
    }

    @ApiOperation(value = "Gets list of unique brands")
    @RequestMapping(path = "/api/brand/brandNames", method = RequestMethod.GET)
    public List<String> getAllUniqueBrands() {

        return bdto.getAllUniqueBrands();
    }

    @ApiOperation(value = "Updates a Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        bdto.update(id,f);
    }




}
