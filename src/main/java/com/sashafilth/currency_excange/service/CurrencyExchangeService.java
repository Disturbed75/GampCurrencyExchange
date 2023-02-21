package com.sashafilth.currency_excange.service;

import com.sashafilth.currency_excange.dto.CurrencyExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class CurrencyExchangeService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyExchangeService.class);
    private final RestTemplate restTemplate;

    @Value("${exchange.usd_uah.endpoint}")
    private String usdUahUrl;


    @Autowired
    public CurrencyExchangeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CurrencyExchangeRate getUahUsdExchangeRate() {
        try {
            CurrencyExchangeRate[] exchangeRates = restTemplate.getForObject(usdUahUrl, CurrencyExchangeRate[].class);
            if (exchangeRates == null || exchangeRates.length == 0) {
                return null;
            }
            return exchangeRates[0];
        } catch (RestClientException e) {
            log.error("Get currency exchange error: " + e.getMessage());
            return null;
        }

    }
}
