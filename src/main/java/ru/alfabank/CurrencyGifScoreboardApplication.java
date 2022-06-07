package ru.alfabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ru.alfabank.client")
public class CurrencyGifScoreboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyGifScoreboardApplication.class, args);
    }

}
