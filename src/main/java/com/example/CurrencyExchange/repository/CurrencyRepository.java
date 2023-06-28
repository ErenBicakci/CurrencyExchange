package com.example.CurrencyExchange.repository;

import com.example.CurrencyExchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> getCurrencyByCurrencyCode(String currencyCode);

}
