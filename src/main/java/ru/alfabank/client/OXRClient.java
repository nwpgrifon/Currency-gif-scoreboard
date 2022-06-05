package ru.alfabank.client;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.dto.OxrResponse;

@FeignClient (name = "oxrClient", url = "${oxr.host}" /*, configuration = OXRConfig.class*/)
public interface OXRClient {


    @GetMapping(path = "/api/time-series.json")
    OxrResponse getExchangeRates(@RequestParam(name="app_id") String appId,
                                 @RequestParam(name="start") String start,
                                 @RequestParam(name="end") String end,
                                 @RequestParam(name="symbols") String symbols);

}
