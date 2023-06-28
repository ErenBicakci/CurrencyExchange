package com.example.CurrencyExchange.util;

import com.example.CurrencyExchange.dto.CurrencyDto;
import com.example.CurrencyExchange.dto.ExchanceRateDto;
import com.example.CurrencyExchange.entity.Currency;
import com.example.CurrencyExchange.entity.ExchanceRate;
import org.springframework.stereotype.Component;

@Component
public class DtoConvert {


    public CurrencyDto convertCurrencyToCurrencyDto(Currency currency) {
        return CurrencyDto.builder()
                .currencyCode(currency.getCurrencyCode())
                .rate(currency.getRate())
                .date(currency.getDate())
                .build();
    }

    public ExchanceRateDto convertExchanceRateToExchanceRateDto(ExchanceRate exchanceRate) {
        return ExchanceRateDto.builder()
                .currencyCodeFrom(exchanceRate.getCurrencyCodeFrom())
                .currencyCodeTo(exchanceRate.getCurrencyCodeTo())
                .amountFrom(exchanceRate.getAmountFrom())
                .rate(exchanceRate.getRate())
                .result(exchanceRate.getResult())
                .date(exchanceRate.getDate())
                .build();
    }



}
