package ru.alfabank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.alfabank.dto.Gif;
import ru.alfabank.dto.GiphyResponse;
import ru.alfabank.dto.OxrResponse;
import ru.alfabank.dto.Rates;
import ru.alfabank.service.CurrencyService;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {"giphy.host= http://localhost:${wiremock.server.port}", "oxr.host = http://localhost:${wiremock.server.port}"})
public class IntegrationTest {


    @Value("${giphy.api_key}")
    private String giphyApiKey;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeAll () {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Rates.class, new RatesSerializer());
        objectMapper.registerModule(module);

    }




    @Test
    void testBroke() throws Exception {
        GiphyResponse giphyResponse = buildGiphyResponse(GIF_URLS_BROKE);
        OxrResponse oxrResponseToday = buildOxrResponse(new BigDecimal(1));
        OxrResponse oxrResponseYesterday = buildOxrResponse(new BigDecimal(2));

        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minus(1, ChronoUnit.DAYS);





        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/gifs/search")).withQueryParams(Map.of("q", equalTo(CurrencyService.BROKE), "api_key", equalTo(giphyApiKey)))
                .willReturn(ok().withBody(objectMapper.writeValueAsString(giphyResponse)).withHeader("Content-Type", "application/json;charset=UTF-8")));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/latest.json"))
                .willReturn(ok().withBody(objectMapper.writeValueAsString(oxrResponseToday)).withHeader("Content-Type", "application/json;charset=UTF-8")));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/historical/" + yesterday.toString() +".json"))
                .willReturn(ok().withBody(objectMapper.writeValueAsString(oxrResponseYesterday)).withHeader("Content-Type", "application/json;charset=UTF-8")));


        String url = mockMvc.perform(get("/currency/RUB"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        assertTrue(GIF_URLS_BROKE.contains(url));


    }

    @Test
    void testRich() throws Exception {
        GiphyResponse giphyResponse = buildGiphyResponse(GIF_URLS_RICH);
        OxrResponse oxrResponseToday = buildOxrResponse(new BigDecimal(12));
        OxrResponse oxrResponseYesterday = buildOxrResponse(new BigDecimal(2));

        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minus(1, ChronoUnit.DAYS);





        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/gifs/search")).withQueryParams(Map.of("q", equalTo(CurrencyService.RICH), "api_key", equalTo(giphyApiKey)))
                .willReturn(ok().withBody(objectMapper.writeValueAsString(giphyResponse)).withHeader("Content-Type", "application/json;charset=UTF-8")));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/latest.json"))
                .willReturn(ok().withBody(objectMapper.writeValueAsString(oxrResponseToday)).withHeader("Content-Type", "application/json;charset=UTF-8")));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/api/historical/" + yesterday.toString() +".json"))
                .willReturn(ok().withBody(objectMapper.writeValueAsString(oxrResponseYesterday)).withHeader("Content-Type", "application/json;charset=UTF-8")));


        String url = mockMvc.perform(get("/currency/RUB"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        assertTrue(GIF_URLS_RICH.contains(url));


    }

    private static List<String> GIF_URLS_RICH = List.of("URL1", "URL2", "URL3", "URL4", "URL5");
    private static List<String> GIF_URLS_BROKE = List.of("URL6", "URL7", "URL8", "URL9", "URL10");


    private GiphyResponse buildGiphyResponse (List<String> gifUrls) {
        GiphyResponse giphyResponse = new GiphyResponse();

        giphyResponse.setResponse(gifUrls.stream().map(url -> {
            Gif gif = new Gif();
            gif.setUrl(url);
            return gif;
        }).collect(Collectors.toList()));
        return giphyResponse;


    }

    private OxrResponse buildOxrResponse (BigDecimal bigDecimal) {
        OxrResponse oxrResponse = new OxrResponse();
        oxrResponse.setRates(new Rates(bigDecimal));
        return oxrResponse;
    }




}