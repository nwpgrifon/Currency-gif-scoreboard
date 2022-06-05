package ru.alfabank.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import ru.alfabank.configuration.RatesDeserializer;

import java.math.BigDecimal;


@JsonDeserialize(using = RatesDeserializer.class)
public class Rates {

    @Getter
    private BigDecimal exchangeRate;



    public Rates(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
