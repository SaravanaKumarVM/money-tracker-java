package com.sarava.moneytracker.controller;

import com.sarava.moneytracker.entity.ExpenseOthers;
import com.sarava.moneytracker.repository.ExpenseOthersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-others")
@CrossOrigin
public class ExpenseOthersController {

    private final ExpenseOthersRepository repo;

    public ExpenseOthersController(ExpenseOthersRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ExpenseOthers> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public ExpenseOthers add(@RequestBody ExpenseOthers expense) {
        return repo.save(expense);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}