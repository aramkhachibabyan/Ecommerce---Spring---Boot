package com.smartCode.ecommerce.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class MicroServiceFeignConfiguration {

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String authHeader = "Basic " + Base64.getEncoder().encodeToString(("ecommerceClient" + ":" + "password").getBytes());
                requestTemplate.header("Authorization", authHeader);
            }
        };

    }
}
