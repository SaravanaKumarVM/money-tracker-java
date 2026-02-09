package com.sarava.moneytracker.repository;

import com.sarava.moneytracker.entity.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    // return single result safely
    Optional<CardInfo> findByBank(String bank);

}
