package com.tinie.otpgen.config;

import com.tinie.otpgen.exceptions.TextLocalErrorHandler;
import com.tinie.otpgen.exceptions.WhatsappMessagingErrorHandler;
import com.tinie.otpgen.utils.HttpSwapResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HttpSwapResponseInterceptor swapResponseInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Scope(value = "prototype")
    public RestTemplate restTemplate(ResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .interceptors(swapResponseInterceptor)
                .errorHandler(errorHandler)
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WhatsappMessagingErrorHandler whatsappMessagingErrorHandler(long phoneNumber) {
        return new WhatsappMessagingErrorHandler(phoneNumber);
    }
}
