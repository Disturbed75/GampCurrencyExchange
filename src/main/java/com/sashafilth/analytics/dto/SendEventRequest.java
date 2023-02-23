package com.sashafilth.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SendEventRequest {

    private String client_id;
    private List<Event> events;

    public SendEventRequest(String client_id) {
        this.client_id = client_id;
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
