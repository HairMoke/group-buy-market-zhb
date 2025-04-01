package com.hb.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http框架
 */

@Configuration
public class OKHttpClientConfig {

    @Bean
    public OkHttpClient httpClient(){
        return new OkHttpClient();
    }
}
