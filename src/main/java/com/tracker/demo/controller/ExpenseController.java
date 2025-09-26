package com.tracker.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.demo.model.Expense;
import com.tracker.demo.model.User;
import com.tracker.demo.service.ExpenseService;
import com.tracker.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping("/{email}")
    public Expense addExpense(@PathVariable String email, @Valid @RequestBody Expense expense) {
        User user = userService.findByEmail(email).orElseThrow();
        expense.setUser(user);
        return expenseService.save(expense);
    }

    @GetMapping("/{email}")
    public List<Expense> listExpenses(@PathVariable String email) {
        User user = userService.findByEmail(email).orElseThrow();
        return expenseService.getUser(user);
    }

    @GetMapping("/summary/{email}")
    public Map<String, Object> getSummary(@PathVariable String email) {
        User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Expense> expenses = expenseService.getUser(user);
        
        double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();
        
        Map<String, Double> categoryWise = expenses.stream()
                .collect(Collectors.groupingBy(
                    Expense::getCategory,
                    Collectors.summingDouble(Expense::getAmount)
                ));
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalAmount", totalAmount);
        summary.put("byCategory", categoryWise);
        
        return summary;
    }
}
