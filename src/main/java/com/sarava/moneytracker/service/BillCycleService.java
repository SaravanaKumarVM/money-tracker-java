package com.sarava.moneytracker.service;

import com.sarava.moneytracker.entity.CardInfo;
import com.sarava.moneytracker.repository.CardInfoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;

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
        CardInfo cardInfo = cardInfoRepository.findByBank(bank);

        if (cardInfo == null) {
            return YearMonth.from(expenseDate);
        }

        int billDate = cardInfo.getBillDate();
        int expenseDay = expenseDate.getDayOfMonth();
        YearMonth expenseMonth = YearMonth.from(expenseDate);

        // If expense day is on or after bill date, it belongs to current month's bill cycle
        if (expenseDay >= billDate) {
            return expenseMonth.plusMonths(1); // Billing month is next month
        } else {
            // If expense day is before bill date, it belongs to previous month's bill cycle
            return expenseMonth; // Billing month is current month
        }
    }

    /**
     * Get the dashboard display month (one month after billing month)
     */
    public YearMonth getDashboardMonth(LocalDate expenseDate, String bank) {
        return getExpenseBillingMonth(expenseDate, bank).plusMonths(1);
    }
}