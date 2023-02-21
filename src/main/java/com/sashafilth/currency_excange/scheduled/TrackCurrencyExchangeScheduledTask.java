package com.sashafilth.currency_excange.scheduled;

import com.sashafilth.analytics.constant.EventName;
import com.sashafilth.analytics.dto.Event;
import com.sashafilth.analytics.dto.SendEventRequest;
import com.sashafilth.analytics.service.AnalyticsService;
import com.sashafilth.currency_excange.dto.CurrencyExchangeRate;
import com.sashafilth.currency_excange.service.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TrackCurrencyExchangeScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(TrackCurrencyExchangeScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final CurrencyExchangeService currencyExchangeService;
    private final AnalyticsService analyticsService;
    @Value("${analytics.client.id}")
    private String analyticsClientId;

    @Autowired
    public TrackCurrencyExchangeScheduledTask(CurrencyExchangeService currencyExchangeService, AnalyticsService analyticsService) {
        this.currencyExchangeService = currencyExchangeService;
        this.analyticsService = analyticsService;
    }


    @Scheduled(fixedDelay = 20, initialDelay = 0, timeUnit = TimeUnit.MINUTES)
    public void run() {
        log.info(TrackCurrencyExchangeScheduledTask.class.getName() + " started: " + dateFormat.format(new Date()));
        CurrencyExchangeRate exchangeRate = currencyExchangeService.getUahUsdExchangeRate();
        if (exchangeRate != null) {
            SendEventRequest request = assembleEventRequest(exchangeRate);
            analyticsService.sendEvent(request);
        }
        log.info(TrackCurrencyExchangeScheduledTask.class.getName() + " finished: " + dateFormat.format(new Date()));
    }

    private SendEventRequest assembleEventRequest(CurrencyExchangeRate exchangeRate) {
        SendEventRequest request = new SendEventRequest(analyticsClientId);
        Event event = new Event(EventName.EXCHANGE_RATE);
        Map<String, Object> params = new HashMap<>();
        params.put("rate", exchangeRate.getRate());
        params.put("pair", "usd_uah");
        event.setParams(params);
        request.addEvent(event);
        return request;
    }


}
