package com.increff.pos.controller;

import com.increff.pos.dto.DailyReportDto;
import com.increff.pos.dto.SalesReportDto;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class SalesReportApiController {

    @Autowired
    private SalesReportDto sdto;

    @ApiOperation(value = "post sales report")
    @RequestMapping(path = "/api/report/sales", method = RequestMethod.POST)
    public List<SalesReportData> add(@RequestBody SalesReportForm form) throws ApiException {
            return sdto.getFilteredSalesReportDateWise(form);
    }

    @ApiOperation(value = "get all sales report")
    @RequestMapping(path = "/api/report/sales", method = RequestMethod.GET)
    public List<SalesReportData> getAll() throws ApiException {
        return sdto.getAll();
    }



}
