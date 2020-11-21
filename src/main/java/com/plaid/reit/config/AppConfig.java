package com.plaid.reit.config;

import com.plaid.client.PlaidClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${plaid.api.client}")
    private String client;
    @Value("${plaid.api.secret}")
    private String secret;

    @Bean
    public PlaidClient plaidClient() {
        return PlaidClient.newBuilder()
                .clientIdAndSecret(client, secret)
                .developmentBaseUrl()
                .build();
    }

}
