package com.sarava.moneytracker.repository;

import com.sarava.moneytracker.dto.CategorySummary;
import com.sarava.moneytracker.entity.ExpenseCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseCardRepository extends JpaRepository<ExpenseCard, Long> {

    @Query("""
SELECT e.category as category, SUM(e.amount) as total
FROM ExpenseCard e
GROUP BY e.category
""")
    List<CategorySummary> getCategorySummary();

}
