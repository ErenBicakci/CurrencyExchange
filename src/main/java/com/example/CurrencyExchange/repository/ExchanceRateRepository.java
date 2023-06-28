package com.example.CurrencyExchange.repository;

import com.example.CurrencyExchange.entity.ExchanceRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchanceRateRepository extends JpaRepository<ExchanceRate, Integer> {
}
