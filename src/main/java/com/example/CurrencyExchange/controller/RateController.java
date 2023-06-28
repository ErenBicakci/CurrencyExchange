package com.example.CurrencyExchange.controller;

import com.example.CurrencyExchange.dto.CurrencyDto;
import com.example.CurrencyExchange.dto.ExchanceRateDto;
import com.example.CurrencyExchange.service.RateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/rate")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping
    public ResponseEntity<CurrencyDto> getRate(@RequestParam String code) {
        CurrencyDto currencyDto = rateService.getRate(code);
        return ResponseEntity.ok(currencyDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyDto>> getAllRates() {
        List<CurrencyDto> currencyDto = rateService.getAllRates();
        return ResponseEntity.ok(currencyDto);
    }

}
