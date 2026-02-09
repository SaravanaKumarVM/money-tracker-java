package com.sarava.moneytracker.controller;

import com.sarava.moneytracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService service;

    @GetMapping("/category-summary")
    public Map<String, Double> categorySummary() {
        return service.getCategoryWiseSummary();
    }
}
