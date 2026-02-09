package com.sarava.moneytracker.repository;

import com.sarava.moneytracker.dto.CategorySummary;
import com.sarava.moneytracker.entity.CardInfo;
import com.sarava.moneytracker.entity.ExpenseOthers;
import com.sarava.moneytracker.entity.FixedExpense;
import com.sarava.moneytracker.entity.IncomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseOthersRepository extends JpaRepository<ExpenseOthers, Long> {

    @Query("""
SELECT e.category as category, SUM(e.amount) as total
FROM ExpenseOthers e
GROUP BY e.category
""")
    List<CategorySummary> getCategorySummary();

}

