package com.sarava.moneytracker.service;

import com.sarava.moneytracker.entity.CardInfo;
import com.sarava.moneytracker.repository.CardInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardInfoService {

    private final CardInfoRepository cardInfoRepository;

    public CardInfoService(CardInfoRepository cardInfoRepository) {
        this.cardInfoRepository = cardInfoRepository;
    }

    public Optional<CardInfo> getByBank(String bank) {
        return cardInfoRepository.findByBank(bank);
    }
}
