package com.increff.pos.controller;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.dto.ReportDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.DailyReportData;
import com.increff.pos.model.DailyReportForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ReportApiController {

    @Autowired
    private ReportDto rdto;

    @ApiOperation(value = "post daily report")
    @RequestMapping(path = "/api/report/daily", method = RequestMethod.POST)
    public List<DailyReportData> add(@RequestBody DailyReportForm form) throws ApiException {
            return rdto.getFilteredReport(form);
    }

    @ApiOperation(value = "post daily report")
    @RequestMapping(path = "/api/report/daily", method = RequestMethod.GET)
    public List<DailyReportData> getAll() throws ApiException {
        return rdto.getAll();
    }






}
