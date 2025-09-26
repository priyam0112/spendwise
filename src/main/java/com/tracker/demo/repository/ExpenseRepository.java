package com.tracker.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tracker.demo.model.Expense;
import com.tracker.demo.model.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID>{
    List<Expense> findByUser(User user);
}
