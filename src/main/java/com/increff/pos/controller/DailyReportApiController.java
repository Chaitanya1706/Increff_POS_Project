package com.increff.pos.controller;
import com.increff.pos.dto.DailyReportDto;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class DailyReportApiController {

    @Autowired
    private DailyReportDto ddto;

    @ApiOperation(value = "post daily report")
    @RequestMapping(path = "/api/report/daily", method = RequestMethod.POST)
    public List<DailyReportData> add(@RequestBody DailyReportForm form) throws ApiException {
            return ddto.getFilteredReport(form);
    }

    @ApiOperation(value = "post daily report")
    @RequestMapping(path = "/api/report/daily", method = RequestMethod.GET)
    public List<DailyReportData> getAll() throws ApiException {
        return ddto.getAll();
    }



}
