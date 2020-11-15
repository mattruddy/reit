package com.plaid.reit.config;

import com.plaid.client.PlaidClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static final String client = "5faf5102744b4600133920fc";
    private static final String secret = "bf325df1afcf566b89693729a84865";

    @Bean
    public PlaidClient plaidClient() {
        return PlaidClient.newBuilder()
                .clientIdAndSecret(client, secret)
                .sandboxBaseUrl()
                .build();
    }

}
