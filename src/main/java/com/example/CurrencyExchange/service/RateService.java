package com.example.CurrencyExchange.service;

import com.example.CurrencyExchange.dto.CurrencyDto;
import com.example.CurrencyExchange.dto.ExchanceRateDto;
import com.example.CurrencyExchange.entity.Currency;
import com.example.CurrencyExchange.entity.ExchanceRate;
import com.example.CurrencyExchange.exception.CurrencyNotFoundException;
import com.example.CurrencyExchange.repository.CurrencyRepository;
import com.example.CurrencyExchange.repository.ExchanceRateRepository;
import com.example.CurrencyExchange.util.DtoConvert;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RateService {


    private final CurrencyRepository currencyRepository;
    private final ExchanceRateRepository exchanceRateRepository;
    private final DtoConvert dtoConvert;

    RateService(CurrencyRepository currencyRepository, ExchanceRateRepository exchanceRateRepository, DtoConvert dtoConvert){
        this.currencyRepository = currencyRepository;
        this.exchanceRateRepository = exchanceRateRepository;
        this.dtoConvert = dtoConvert;
        loadCurrentRates();
    }

    @Scheduled( cron = "0 * * * * ?")
    public void loadCurrentRates(){
        List<Currency> currencies = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.akbank.com/_vti_bin/AkbankServicesSecure/FrontEndServiceSecure.svc/GetCurrencyRates"))
                    .build();
            String text = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body().replace("\\u000d","").replace("\\u000a","").replace("\\u0027","").replace("  "," ");
            JSONObject obj = new JSONObject(text);
            JSONObject obj2 = new JSONObject(obj.getString("GetCurrencyRatesResult"));

            JSONArray paritiesJSON = obj2.getJSONArray("cur");
            for (int i = 0;i < paritiesJSON.length() ;i += 2){
                Currency currency = Currency.builder()
                        .currencyCode(paritiesJSON.getJSONObject(i).getString("Title"))
                        .rate(paritiesJSON.getJSONObject(i).getDouble("USDCaprazKur"))
                        .date(new Date())
                        .build();

                Currency oldCurrency = currencyRepository.getCurrencyByCurrencyCode(currency.getCurrencyCode()).orElse(null);
                if (oldCurrency != null){
                    oldCurrency.setRate(currency.getRate());
                    oldCurrency.setDate(currency.getDate());
                    currencies.add(oldCurrency);
                }
                else {
                    currencies.add(currency);
                }
            }
            currencyRepository.saveAll(currencies);

        }
        catch (Exception e){
            log.error("Cannot get currency rates from Akbank API : " + e.getMessage());
        }

    }

    public CurrencyDto getRate(String currencyCode){
        Currency currency = currencyRepository.getCurrencyByCurrencyCode(currencyCode).orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));
        return CurrencyDto.builder().date(new Date()).currencyCode(currencyCode).rate(currency.getRate()).build();

    }

    public List<CurrencyDto> getAllRates() {
        List<Currency> currencies = currencyRepository.findAll();
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyDtos.add(CurrencyDto.builder().date(new Date()).currencyCode(currency.getCurrencyCode()).rate(currency.getRate()).build());
        }
        return currencyDtos;
    }







}
