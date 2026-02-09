package com.sarava.moneytracker.service;

import com.sarava.moneytracker.entity.CardInfo;
import com.sarava.moneytracker.repository.CardInfoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@Service
public class BillCycleService {

    private final CardInfoRepository cardInfoRepository;

    public BillCycleService(CardInfoRepository cardInfoRepository) {
        this.cardInfoRepository = cardInfoRepository;
    }

    /**
     * Calculate which month an expense should be billed in based on the card's bill cycle
     *
     * Example:
     * - HDFC bill date: 10th of each month
     * - Expense date: Jan 18
     * - Bill cycle: Jan 11 - Feb 10 (covers this expense)
     * - Billing month: February
     * - Dashboard month: March (next month after billing)
     */
    public YearMonth getExpenseBillingMonth(LocalDate expenseDate, String bank) {

        Optional<CardInfo> optionalCardInfo = cardInfoRepository.findByBank(bank);

        if (optionalCardInfo.isEmpty()) {
            return YearMonth.from(expenseDate);
        }

        CardInfo cardInfo = optionalCardInfo.get();

        int billDate = cardInfo.getBillDate();
        int expenseDay = expenseDate.getDayOfMonth();
        YearMonth expenseMonth = YearMonth.from(expenseDate);

        if (expenseDay >= billDate) {
            return expenseMonth.plusMonths(1);
        } else {
            return expenseMonth;
        }
    }


    /**
     * Get the dashboard display month (one month after billing month)
     */
    public YearMonth getDashboardMonth(LocalDate expenseDate, String bank) {
        return getExpenseBillingMonth(expenseDate, bank).plusMonths(1);
    }
}