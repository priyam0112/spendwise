package com.tracker.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 50, message = "Name must be 3-50 characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 5, message = "Password must be at least 5 characters")
    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();
}
