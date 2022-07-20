package com.tinie.otpgen.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.constants")
@Data
public class EnvConstants {
    private String textLocalApiKey;
    private String whatsappTemplateName;
    private String whatsappAuthKey;
    private String whatsappMessageUrl;
}
