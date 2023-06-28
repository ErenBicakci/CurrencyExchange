package com.example.CurrencyExchange.controller;


import com.example.CurrencyExchange.dto.ExchanceRateDto;
import com.example.CurrencyExchange.service.ExchanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exchance")
public class ExchanceController {
    private final ExchanceService exchanceService;

    public ExchanceController(ExchanceService exchanceService) {
        this.exchanceService = exchanceService;
    }

    @GetMapping
    public ResponseEntity<ExchanceRateDto> exchanceRate(@RequestParam String from, @RequestParam String to, @RequestParam Double amount) {
        ExchanceRateDto exchanceRateDto = exchanceService.exchanceRate(from, to, amount);
        return ResponseEntity.ok(exchanceRateDto);
    }
}
