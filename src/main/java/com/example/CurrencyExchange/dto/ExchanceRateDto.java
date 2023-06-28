package com.example.CurrencyExchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchanceRateDto {

    private String currencyCodeFrom;
    private String currencyCodeTo;
    private double amountFrom;
    private double rate; // 1 currencyCodeFrom = rate currencyCodeTo
    private double result;

    private Date date;

}
