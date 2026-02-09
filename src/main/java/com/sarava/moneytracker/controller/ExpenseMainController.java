package com.sarava.moneytracker.controller;

import com.sarava.moneytracker.entity.ExpenseMain;
import com.sarava.moneytracker.repository.ExpenseMainRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-main")
@CrossOrigin
public class ExpenseMainController {

    private final ExpenseMainRepository repo;

    public ExpenseMainController(ExpenseMainRepository repo) {
        this.repo = repo;
    }

    // GET all
    @GetMapping
    public List<ExpenseMain> getAll() {
        return repo.findAll();
    }

    // POST add
    @PostMapping
    public ExpenseMain add(@RequestBody ExpenseMain expense) {
        return repo.save(expense);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
