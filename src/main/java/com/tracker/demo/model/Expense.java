package com.tracker.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "expenses")
@Data
public class Expense {
    
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Amount can't be null")
    private Double amount;

    @NotNull(message = "Amount can't be null")
    @Size(min = 2, max = 5)
    private String category;

    private String note;

    @NotNull(message = "Occurred date cannot be null")
    private LocalDateTime occurredAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    public void setUser(User user){
        this.user = user;
    }


}
