package com.tracker.demo.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tracker.demo.model.Expense;
import com.tracker.demo.model.User;
import com.tracker.demo.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepo;

    public ExpenseService(ExpenseRepository expenseRepo){
        this.expenseRepo = expenseRepo;
    }

    @CacheEvict(value = "expenses", key = "#e.user.email")
    public Expense save(Expense e){
        return expenseRepo.save(e);
    }

    @Cacheable(value="expenses", key="#user.email")
    public List<Expense> getUser(User user){
        return expenseRepo.findByUser(user);
    }

}
