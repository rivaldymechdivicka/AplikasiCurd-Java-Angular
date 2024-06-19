package com.example.Backend.controller;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.service.ReportService;


@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/transaction")
    public ResponseEntity<byte[]> generateTransactionReport(
        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        byte[] reportBytes = reportService.generateTransactionReport(startDate, endDate);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=transactionReport.pdf");
        return ResponseEntity.ok().headers(headers).body(reportBytes);
    }
}
