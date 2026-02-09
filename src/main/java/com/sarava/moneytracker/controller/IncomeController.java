package com.sarava.moneytracker.controller;

import com.sarava.moneytracker.entity.IncomeInfo;
import com.sarava.moneytracker.repository.IncomeInfoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@CrossOrigin
public class IncomeController {

    private final IncomeInfoRepository repo;

    public IncomeController(IncomeInfoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<IncomeInfo> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public IncomeInfo add(@RequestBody IncomeInfo income) {
        return repo.save(income);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}