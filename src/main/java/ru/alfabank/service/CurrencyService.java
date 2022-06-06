package ru.alfabank.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfabank.client.GiphyClient;
import ru.alfabank.client.OXRClient;
import ru.alfabank.dto.Gif;
import ru.alfabank.dto.OxrResponse;
import ru.alfabank.dto.Rates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Service
public class CurrencyService {


    public static ThreadLocal<String> currencyCodeStorage = new ThreadLocal<>();;


    public static final String BROKE = "broke";
    public static final String RICH = "rich";
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
        Random rand = new Random();
        currencyCodeStorage.set(currencyCode);
        boolean amIrich = calculateXRDifference(currencyCode);
        List<Gif> gifs = giphyClient.getGif(giphyApiKey, amIrich ? RICH : BROKE).getData();
        int randomGif = rand.nextInt(gifs.size());
        return gifs.get(randomGif).getUrl();
    }



    private boolean calculateXRDifference(String current) {
        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minus(1, ChronoUnit.DAYS);
        OxrResponse exchangeRatesToday = oxrClient.getExchangeRatesForToday(oxrAppId);
        OxrResponse exchangeRatesYesterday = oxrClient.getExchangeRatesForYesterday(yesterday.toString(), oxrAppId);
        Rates ratesToday = exchangeRatesToday.getRates();
        Rates ratesYesterday = exchangeRatesYesterday.getRates();
        return ratesToday.getExchangeRate().compareTo(ratesYesterday.getExchangeRate()) > 0;
    }
}
