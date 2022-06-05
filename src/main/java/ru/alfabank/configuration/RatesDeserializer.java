package ru.alfabank.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import ru.alfabank.dto.Rates;
import ru.alfabank.service.CurrencyService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RatesDeserializer extends StdDeserializer<Rates> {

    public RatesDeserializer() {
        this(null);
    }

    public RatesDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Rates deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minus(1, ChronoUnit.DAYS);
        JsonNode jsonNodeToday = node.get(currentDate.toString());
        JsonNode jsonNodeYesterday = node.get(yesterday.toString());
        BigDecimal todayExchangeRate = jsonNodeToday.get(CurrencyService.currencyCodeStorage.get()).decimalValue();
        BigDecimal yesterdayExchangeRate = jsonNodeYesterday.get(CurrencyService.currencyCodeStorage.get()).decimalValue();
        return new Rates(yesterdayExchangeRate, todayExchangeRate);
    }
}