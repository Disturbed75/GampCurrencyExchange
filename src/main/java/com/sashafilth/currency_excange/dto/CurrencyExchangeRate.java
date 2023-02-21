package com.sashafilth.currency_excange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CurrencyExchangeRate implements Serializable {

    private Integer r030;
    private String txt;
    private String cc;
    private Float rate;
    private String exchangedate;

}
