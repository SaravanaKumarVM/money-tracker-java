package com.sarava.moneytracker.repository;

import com.sarava.moneytracker.entity.ExpenseMain;
import com.sarava.moneytracker.dto.CategorySummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseMainRepository extends JpaRepository<ExpenseMain, Long> {

    @Query("""
        SELECT e.category as category, SUM(e.amount) as total
        FROM ExpenseMain e
        GROUP BY e.category
    """)
    List<CategorySummary> getCategorySummary();
}
