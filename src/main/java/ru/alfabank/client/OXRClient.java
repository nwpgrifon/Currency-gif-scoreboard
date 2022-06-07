package ru.alfabank.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.dto.OxrResponse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@FeignClient (name = "oxrClient", url = "${oxr.host}" /*, configuration = OXRConfig.class*/)
public interface OXRClient {

    @GetMapping(path = "/api/latest.json")
    OxrResponse getExchangeRatesForToday(@RequestParam(name="app_id") String appId);

    @GetMapping(path = "/api/historical/{date}.json")
    OxrResponse getExchangeRatesForYesterday(@PathVariable("date") String date, @RequestParam(name="app_id") String appId);

}
