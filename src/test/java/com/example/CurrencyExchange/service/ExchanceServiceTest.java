package com.example.CurrencyExchange.service;

import com.example.CurrencyExchange.dto.CurrencyDto;
import com.example.CurrencyExchange.dto.ExchanceRateDto;
import com.example.CurrencyExchange.entity.ExchanceRate;
import com.example.CurrencyExchange.repository.CurrencyRepository;
import com.example.CurrencyExchange.repository.ExchanceRateRepository;
import com.example.CurrencyExchange.util.DtoConvert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Date;


class ExchanceServiceTest {

    @Mock
    private  RateService rateService;

    @Spy
    private DtoConvert dtoConvert;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchanceRateRepository exchanceRateRepository;
    private ExchanceService exchanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        exchanceService = new ExchanceService(currencyRepository,exchanceRateRepository,dtoConvert,rateService);
    }

    @Test
    void testExchanceRate() {
        Mockito.when(rateService.getRate("USD")).thenReturn(CurrencyDto.builder().date(new Date()).rate(1).currencyCode("USD").build());
        Mockito.when(rateService.getRate("EUR")).thenReturn(CurrencyDto.builder().date(new Date()).rate(1.2).currencyCode("EUR").build());
        Mockito.when(exchanceRateRepository.save(Mockito.any())).thenReturn(null);
        ExchanceRateDto exchanceRateDto = exchanceService.exchanceRate("EUR", "USD", 100.0);
        assert(exchanceRateDto.getResult() == 120.0);
        assert(exchanceRateDto.getRate() == 1.2);
        assert(exchanceRateDto.getAmountFrom() == 100.0);
        assert(exchanceRateDto.getCurrencyCodeFrom().equals("EUR"));
        assert(exchanceRateDto.getCurrencyCodeTo().equals("USD"));
        Mockito.verify(exchanceRateRepository,Mockito.times(1)).save(Mockito.any(ExchanceRate.class));
    }

}