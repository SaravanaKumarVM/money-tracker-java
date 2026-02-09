package com.sarava.moneytracker.repository;

import com.sarava.moneytracker.entity.CardInfo;
import com.sarava.moneytracker.entity.FixedExpense;
import com.sarava.moneytracker.entity.IncomeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeInfoRepository extends JpaRepository<IncomeInfo, Long> {}
