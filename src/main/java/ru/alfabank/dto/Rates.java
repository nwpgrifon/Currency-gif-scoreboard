package ru.alfabank.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.alfabank.configuration.RatesDeserializer;

import java.math.BigDecimal;


@JsonDeserialize(using = RatesDeserializer.class)
public class Rates {

    private BigDecimal yesterdaysER;

    private BigDecimal todaysER;

    public BigDecimal getYesterdaysER() {
        return yesterdaysER;
    }

    public BigDecimal getTodaysER() {
        return todaysER;
    }

    public Rates(BigDecimal yesterdaysER, BigDecimal todaysER) {
        this.yesterdaysER = yesterdaysER;
        this.todaysER = todaysER;
    }

}
