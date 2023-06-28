package com.example.CurrencyExchange.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchanceRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String currencyCodeFrom;

    private String currencyCodeTo;
    private double amountFrom;
    private double rate; // 1 currencyCodeFrom = rate currencyCodeTo
    private double result;

    private Date date;
}
