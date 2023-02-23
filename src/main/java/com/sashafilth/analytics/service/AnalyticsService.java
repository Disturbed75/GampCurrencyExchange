package com.sashafilth.analytics.service;

import com.sashafilth.analytics.dto.SendEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);
    private final RestTemplate restTemplate;
    @Value("${analytics.endpoint}")
    private String analyticsUrl;


    @Autowired
    public AnalyticsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendEvent(SendEventRequest request) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "spring-boot-app");
            headers.set("Content-Type", "application/json");
            final HttpEntity<Map<String, Object>> entity = new HttpEntity(request, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(analyticsUrl, entity, String.class);
            if (!response.getStatusCode().equals(HttpStatus.NO_CONTENT) && !(response.getStatusCode().equals(HttpStatus.OK))) {
                throw new RestClientException("Send analytics event error. Wrong response status");
            }
        } catch (RestClientException e) {
            log.error("Send event error" + e.getMessage());
        }
    }
}
