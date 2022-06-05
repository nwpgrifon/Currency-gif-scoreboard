package ru.alfabank.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfabank.client.GiphyClient;
import ru.alfabank.client.OXRClient;
import ru.alfabank.dto.OxrResponse;
import ru.alfabank.dto.Rates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CurrencyService {

    public static ThreadLocal<String> currencyCodeStorage;


    private static final String BROKE = "broke";
    private static final String RICH = "rich";
    @Value("${giphy.api_key}")
    private String giphyApiKey;

    @Value ("${oxr.appId}")
    private String oxrAppId;



    private final GiphyClient giphyClient;
    private final OXRClient oxrClient;


    public CurrencyService(GiphyClient giphyClient, OXRClient oxrClient) {
        this.giphyClient = giphyClient;
        this.oxrClient = oxrClient;
    }


    public String getGifForCurrency(String currencyCode) { // TODO currency code validator
        currencyCodeStorage.set(currencyCode);
        boolean amIrich = calculateXRDifference(currencyCode);
        return giphyClient.getGif(giphyApiKey, amIrich ? RICH : BROKE).getData().get(0).getUrl();
    }



    private boolean calculateXRDifference(String current) {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minus(1, ChronoUnit.DAYS);
        OxrResponse exchangeRates = oxrClient.getExchangeRates(oxrAppId, currentDate.toString(), yesterday.toString(), current);
        Rates rates = exchangeRates.getRates();
        return rates.getTodaysER().compareTo(rates.getYesterdaysER()) > 0;
    }
}
