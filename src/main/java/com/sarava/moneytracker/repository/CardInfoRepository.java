package com.sarava.moneytracker.repository;

import com.sarava.moneytracker.entity.CardInfo;
import com.sarava.moneytracker.entity.FixedExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {}

