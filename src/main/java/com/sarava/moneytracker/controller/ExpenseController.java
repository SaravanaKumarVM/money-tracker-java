//package com.sarava.moneytracker.controller;
//
//import com.sarava.moneytracker.entity.ExpenseMain;
//import com.sarava.moneytracker.repository.ExpenseMainRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/expenses")
//@CrossOrigin("*")
//public class ExpenseController {
//
//    @Autowired
//    private ExpenseMainRepository repo;
//
//    @GetMapping
//    public List<ExpenseMain> getAll() {
//        return repo.findAll();
//    }
//
//    @PostMapping
//    public ExpenseMain create(@RequestBody ExpenseMain e) {
//        return repo.save(e);
//    }
//
//    @PutMapping("/{id}")
//    public ExpenseMain update(@PathVariable Long id, @RequestBody ExpenseMain e) {
//        e.setId(id);
//        return repo.save(e);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        repo.deleteById(id);
//    }
//}
