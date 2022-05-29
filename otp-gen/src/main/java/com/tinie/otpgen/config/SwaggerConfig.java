package com.tinie.otpgen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi docApi(){
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public OpenAPI getAppInfo(){
        return new OpenAPI()
                .info(new Info().title("Tinie API")
                        .description("Tinie OTP Backend")
                        .version("v1.0")
                        .license(new License().name("LICENSE").url("License Url")));
    }
}
