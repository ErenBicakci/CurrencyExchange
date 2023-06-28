package com.example.CurrencyExchange.service;

import com.example.CurrencyExchange.dto.CurrencyDto;
import com.example.CurrencyExchange.dto.ExchanceRateDto;
import com.example.CurrencyExchange.entity.ExchanceRate;
import com.example.CurrencyExchange.repository.CurrencyRepository;
import com.example.CurrencyExchange.repository.ExchanceRateRepository;
import com.example.CurrencyExchange.util.DtoConvert;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ExchanceService {

    private final CurrencyRepository currencyRepository;
    private final ExchanceRateRepository exchanceRateRepository;
    private final DtoConvert dtoConvert;
    private final RateService rateService;

    ExchanceService(CurrencyRepository currencyRepository, ExchanceRateRepository exchanceRateRepository, DtoConvert dtoConvert, RateService rateService){
        this.currencyRepository = currencyRepository;
        this.exchanceRateRepository = exchanceRateRepository;
        this.dtoConvert = dtoConvert;
        this.rateService = rateService;
    }


    public ExchanceRateDto exchanceRate(String from, String to, Double amount) {
        CurrencyDto currencyFrom = rateService.getRate(from);
        CurrencyDto currencyTo = rateService.getRate(to);
        Double rate = currencyFrom.getRate() / currencyTo.getRate();
        Double result = amount * rate;

        ExchanceRate exchanceRate = ExchanceRate.builder()
                .currencyCodeFrom(from)
                .currencyCodeTo(to)
                .amountFrom(amount)
                .rate(rate)
                .result(result)
                .date(new Date())
                .build();

        exchanceRateRepository.save(exchanceRate);
        return dtoConvert.convertExchanceRateToExchanceRateDto(exchanceRate);
    }
}
