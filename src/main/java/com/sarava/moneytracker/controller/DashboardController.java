package com.sarava.moneytracker.controller;

import com.sarava.moneytracker.dto.CategorySummary;
import com.sarava.moneytracker.entity.ExpenseCard;
import com.sarava.moneytracker.entity.ExpenseMain;
import com.sarava.moneytracker.entity.ExpenseOthers;
import com.sarava.moneytracker.entity.IncomeInfo;
import com.sarava.moneytracker.repository.ExpenseCardRepository;
import com.sarava.moneytracker.repository.ExpenseMainRepository;
import com.sarava.moneytracker.repository.ExpenseOthersRepository;
import com.sarava.moneytracker.repository.IncomeInfoRepository;
import com.sarava.moneytracker.service.BillCycleService;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DashboardController {

    private final ExpenseMainRepository expenseMainRepo;
    private final ExpenseCardRepository expenseCardRepo;
    private final ExpenseOthersRepository expenseOthersRepo;
    private final IncomeInfoRepository incomeInfoRepo;
    private final BillCycleService billCycleService;

    public DashboardController(ExpenseMainRepository expenseMainRepo,
                               ExpenseCardRepository expenseCardRepo,
                               ExpenseOthersRepository expenseOthersRepo,
                               IncomeInfoRepository incomeInfoRepo,
                               BillCycleService billCycleService) {
        this.expenseMainRepo = expenseMainRepo;
        this.expenseCardRepo = expenseCardRepo;
        this.expenseOthersRepo = expenseOthersRepo;
        this.incomeInfoRepo = incomeInfoRepo;
        this.billCycleService = billCycleService;
    }

    /**
     * Get today's expenses
     */
    @GetMapping("/today")
    public Double getToday() {
        LocalDate today = LocalDate.now();
        double mainExpense = expenseMainRepo.findAll().stream()
                .filter(e -> e.getDate().equals(today))
                .mapToDouble(ExpenseMain::getAmount)
                .sum();

        double othersExpense = expenseOthersRepo.findAll().stream()
                .filter(e -> e.getDate().equals(today))
                .mapToDouble(ExpenseOthers::getAmount)
                .sum();

        return mainExpense + othersExpense;
    }

    /**
     * Get current month's expenses (considering bill cycles for cards)
     */
    @GetMapping("/month")
    public Double getMonth() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate today = LocalDate.now();

        // Get main expenses for current month
        double mainExpense = expenseMainRepo.findAll().stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(currentMonth))
                .mapToDouble(ExpenseMain::getAmount)
                .sum();

        // Get others expenses for current month
        double othersExpense = expenseOthersRepo.findAll().stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(currentMonth))
                .mapToDouble(ExpenseOthers::getAmount)
                .sum();

        // Get card expenses adjusted for bill cycles
        double cardExpense = expenseCardRepo.findAll().stream()
                .filter(e -> billCycleService.getDashboardMonth(e.getDate(), e.getBank()).equals(currentMonth))
                .mapToDouble(ExpenseCard::getAmount)
                .sum();

        // Get income for current month
        double income = incomeInfoRepo.findAll().stream()
                .filter(i -> YearMonth.from(i.getDate()).equals(currentMonth))
                .mapToDouble(IncomeInfo::getAmount)
                .sum();

        return new Double[]{mainExpense + othersExpense + cardExpense, income}[0];
    }

    /**
     * Get overall expenses (all-time)
     */
    @GetMapping("/overall")
    public Double getOverall() {
        double mainExpense = expenseMainRepo.findAll().stream()
                .mapToDouble(ExpenseMain::getAmount)
                .sum();

        double othersExpense = expenseOthersRepo.findAll().stream()
                .mapToDouble(ExpenseOthers::getAmount)
                .sum();

        double cardExpense = expenseCardRepo.findAll().stream()
                .mapToDouble(ExpenseCard::getAmount)
                .sum();

        return mainExpense + othersExpense + cardExpense;
    }

    /**
     * Get category-wise summary from all expense types
     */
    @GetMapping("/category-summary")
    public Map<String, Double> getCategorySummary() {
        Map<String, Double> summary = new HashMap<>();

        // Add from ExpenseMain
        expenseMainRepo.getCategorySummary().forEach(cs -> {
            summary.put(cs.getCategory(), summary.getOrDefault(cs.getCategory(), 0.0) + cs.getTotal());
        });

        // Add from ExpenseCard
        expenseCardRepo.getCategorySummary().forEach(cs -> {
            summary.put(cs.getCategory(), summary.getOrDefault(cs.getCategory(), 0.0) + cs.getTotal());
        });

        // Add from ExpenseOthers
        expenseOthersRepo.getCategorySummary().forEach(cs -> {
            summary.put(cs.getCategory(), summary.getOrDefault(cs.getCategory(), 0.0) + cs.getTotal());
        });

        return summary;
    }

    /**
     * Get month-wise income vs expense comparison
     */
    @GetMapping("/income-expense-comparison")
    public Map<String, Map<String, Double>> getIncomeExpenseComparison() {
        Map<String, Map<String, Double>> comparison = new TreeMap<>();

        // Collect all months
        Set<YearMonth> months = new HashSet<>();

        incomeInfoRepo.findAll().forEach(i -> months.add(YearMonth.from(i.getDate())));
        expenseMainRepo.findAll().forEach(e -> months.add(YearMonth.from(e.getDate())));
        expenseOthersRepo.findAll().forEach(e -> months.add(YearMonth.from(e.getDate())));
        expenseCardRepo.findAll().forEach(e -> months.add(billCycleService.getDashboardMonth(e.getDate(), e.getBank())));

        // Calculate for each month
        months.forEach(month -> {
            double income = incomeInfoRepo.findAll().stream()
                    .filter(i -> YearMonth.from(i.getDate()).equals(month))
                    .mapToDouble(IncomeInfo::getAmount)
                    .sum();

            double mainExpense = expenseMainRepo.findAll().stream()
                    .filter(e -> YearMonth.from(e.getDate()).equals(month))
                    .mapToDouble(ExpenseMain::getAmount)
                    .sum();

            double othersExpense = expenseOthersRepo.findAll().stream()
                    .filter(e -> YearMonth.from(e.getDate()).equals(month))
                    .mapToDouble(ExpenseOthers::getAmount)
                    .sum();

            double cardExpense = expenseCardRepo.findAll().stream()
                    .filter(e -> billCycleService.getDashboardMonth(e.getDate(), e.getBank()).equals(month))
                    .mapToDouble(ExpenseCard::getAmount)
                    .sum();

            Map<String, Double> monthData = new HashMap<>();
            monthData.put("income", income);
            monthData.put("expense", mainExpense + othersExpense + cardExpense);
            monthData.put("balance", income - (mainExpense + othersExpense + cardExpense));

            comparison.put(month.toString(), monthData);
        });

        return comparison;
    }

    /**
     * Get card expenses grouped by bank and bill cycle
     */
    @GetMapping("/card-expenses-by-bank")
    public Map<String, Map<String, Double>> getCardExpensesByBank() {
        Map<String, Map<String, Double>> bankExpenses = new HashMap<>();

        expenseCardRepo.findAll().forEach(expense -> {
            String bank = expense.getBank();
            YearMonth billingMonth = billCycleService.getDashboardMonth(expense.getDate(), bank);

            bankExpenses.computeIfAbsent(bank, k -> new TreeMap<>())
                    .put(billingMonth.toString(),
                            bankExpenses.get(bank).getOrDefault(billingMonth.toString(), 0.0) + expense.getAmount());
        });

        return bankExpenses;
    }
}