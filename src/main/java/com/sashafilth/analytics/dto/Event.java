package com.sashafilth.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@ToString
public class Event {

    private String name;
    private Map<String, Object> params;

    public Event(String name) {
        this.name = name;
        this.params = Collections.emptyMap();
    }
}
