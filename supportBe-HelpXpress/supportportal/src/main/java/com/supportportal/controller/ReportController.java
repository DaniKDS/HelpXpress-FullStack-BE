package com.supportportal.controller;

import com.supportportal.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/addBulkReports")
    public ResponseEntity<String> addBulkReports() {
        reportService.addBulkReports();
        return ResponseEntity.ok("50 de raportări au fost adăugate cu succes.");
    }
}
