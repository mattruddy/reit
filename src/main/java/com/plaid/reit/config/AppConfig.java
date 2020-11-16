package com.plaid.reit.config;

import com.plaid.client.PlaidClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final String client = "5faf5102744b4600133920fc";
    private static final String secret = "5e02c0028cee432df68a2cf563767b";

    @Bean
    public PlaidClient plaidClient() {
        return PlaidClient.newBuilder()
                .clientIdAndSecret(client, secret)
//                .logLevel(HttpLoggingInterceptor.Level.BODY)
                .developmentBaseUrl()
                .build();
    }

}
