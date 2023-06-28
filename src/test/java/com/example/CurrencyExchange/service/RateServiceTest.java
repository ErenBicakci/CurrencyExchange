package com.example.CurrencyExchange.service;

import com.example.CurrencyExchange.dto.CurrencyDto;
import com.example.CurrencyExchange.entity.Currency;
import com.example.CurrencyExchange.repository.CurrencyRepository;
import com.example.CurrencyExchange.repository.ExchanceRateRepository;
import com.example.CurrencyExchange.util.DtoConvert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RateServiceTest {


    @Spy
    private DtoConvert dtoConvert;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchanceRateRepository exchanceRateRepository;
    private RateService rateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rateService = new RateService(currencyRepository,exchanceRateRepository,dtoConvert);
    }



    @Test
    void test_Load_Current_Rates() {
        Mockito.verify(currencyRepository,Mockito.times(1)).saveAll(Mockito.anyList());
    }

    @Test
    void testGetRate() {

        Mockito.when(currencyRepository.getCurrencyByCurrencyCode("USD")).thenReturn(Optional.of(Currency.builder().currencyCode("USD").rate(1.0).date(null).build()));
        CurrencyDto currencyDto = rateService.getRate("USD");
        assert(currencyDto.getCurrencyCode().equals("USD"));
        assert(currencyDto.getRate() == 1.0);
        Mockito.verify(currencyRepository,Mockito.times(2)).getCurrencyByCurrencyCode("USD");

    }

    @Test
    void testGetAllRates() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.builder().currencyCode("USD").rate(1.0).date(null).build());
        currencies.add(Currency.builder().currencyCode("EUR").rate(1.2).date(null).build());
        currencies.add(Currency.builder().currencyCode("GBP").rate(1.3).date(null).build());
        currencies.add(Currency.builder().currencyCode("JPY").rate(1.4).date(null).build());

        Mockito.when(currencyRepository.findAll()).thenReturn(currencies);
        List<CurrencyDto> currencieDtos = rateService.getAllRates();
        assert(currencieDtos.size() == 4);
        assert(currencieDtos.get(0).getCurrencyCode().equals("USD"));
        assert(currencieDtos.get(0).getRate() == 1.0);
        assert(currencieDtos.get(1).getCurrencyCode().equals("EUR"));
        assert(currencieDtos.get(1).getRate() == 1.2);
        assert(currencieDtos.get(2).getCurrencyCode().equals("GBP"));
        assert(currencieDtos.get(2).getRate() == 1.3);

        Mockito.verify(currencyRepository,Mockito.times(1)).findAll();
    }
}