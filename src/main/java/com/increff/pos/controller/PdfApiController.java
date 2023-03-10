package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.dto.PdfDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.PdfData;
import com.increff.pos.service.ApiException;
import com.increff.pos.util.pdf.PDF_Generator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Api
@RestController
public class PdfApiController {

    @Autowired
    private PdfDto pdto;

    @ApiOperation(value = "generate pdf")
    @RequestMapping(path = "/api/pdf/{id}", method = RequestMethod.GET)
    public void get(@PathVariable int id) throws ApiException {
        PDF_Generator pdfGenerator = new PDF_Generator();
        PdfData d = pdto.get(id);
        pdfGenerator.pdf_generator(d);


    }

    @ApiOperation(value = "generate pdf")
    @RequestMapping(path = "/api/pdf/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable int id) throws ApiException, IOException {

        Path pdf = Paths.get("./src/main/resources/apache/PdfFile/"+id+"_invoice.pdf");

        byte[] contents = Files.readAllBytes(pdf);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "Order"+id+"_invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;

    }
 
    //todo -> naming convention plural


}
