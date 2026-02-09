package com.sarava.moneytracker.service;

import com.sarava.moneytracker.dto.CategorySummary;
import com.sarava.moneytracker.repository.ExpenseCardRepository;
import com.sarava.moneytracker.repository.ExpenseMainRepository;
import com.sarava.moneytracker.repository.ExpenseOthersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    ExpenseMainRepository mainRepo;
    @Autowired
    ExpenseCardRepository cardRepo;
    @Autowired
    ExpenseOthersRepository othersRepo;

    public Map<String, Double> getCategoryWiseSummary() {

        Map<String, Double> result = new HashMap<>();

        add(result, mainRepo.getCategorySummary());
        add(result, cardRepo.getCategorySummary());
        add(result, othersRepo.getCategorySummary());

        return result;
    }

    private void add(Map<String, Double> map, List<CategorySummary> list) {
        for (CategorySummary s : list) {
            map.merge(s.getCategory(), s.getTotal(), Double::sum);
        }
    }
}
