package ru.alfabank.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabank.service.CurrencyService;

@RequiredArgsConstructor
@RestController
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping ("/currency/{currencyCode}")
    public String getGifForCurrencyPair(@PathVariable String currencyCode) {
        return currencyService.getGifForCurrency(currencyCode);
    }

}
