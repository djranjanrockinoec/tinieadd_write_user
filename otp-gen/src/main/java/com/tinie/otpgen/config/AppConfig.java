package com.tinie.otpgen.config;

import com.tinie.otpgen.exceptions.TextLocalErrorHandler;
import com.tinie.otpgen.utils.HttpSwapResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TextLocalErrorHandler textLocalErrorHandler;
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
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .interceptors(swapResponseInterceptor)
                .errorHandler(textLocalErrorHandler)
                .build();
    }
}
