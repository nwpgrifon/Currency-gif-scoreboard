package ru.alfabank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfabank.dto.GiphyResponse;
import ru.alfabank.dto.OxrResponse;


@FeignClient(name = "giphyClient", url = "${giphy.host}")
public interface GiphyClient {

    @GetMapping(path = "/v1/gifs/search")
    GiphyResponse getGif(@RequestParam(name="api_key") String apiKey,
                         @RequestParam(name = "q") String query);

}
