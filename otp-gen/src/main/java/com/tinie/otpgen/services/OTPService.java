package com.tinie.otpgen.services;

import com.tinie.otpgen.exceptions.TextLocalErrorHandler;
import com.tinie.otpgen.exceptions.WhatsappMessagingErrorHandler;
import com.tinie.otpgen.utils.Constants;
import com.tinie.otpgen.utils.EnvConstants;
import com.tinie.otpgen.utils.OTPGen;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OTPService {

    private final OTPGen otpGen;
    private final BeanFactory beanFactory;
    private final EnvConstants envConstants;
    private final RestTemplate textLocalRestTemplate;

    @Autowired
    public OTPService(OTPGen otpGen,
                      BeanFactory beanFactory,
                      EnvConstants envConstants,
                      TextLocalErrorHandler textLocalErrorHandler) {
        this.otpGen = otpGen;
        this.beanFactory = beanFactory;
        this.envConstants = envConstants;
        this.textLocalRestTemplate = beanFactory.getBean(RestTemplate.class, textLocalErrorHandler);
    }

    private WhatsappMessagingErrorHandler whatsappMessagingErrorHandler(long phoneNumber) {
        return beanFactory.getBean(WhatsappMessagingErrorHandler.class, phoneNumber);
    }

    public String sendOTP(long phoneNumber) {

        var otp = otpGen.gen6DigitOTP();

        //send otp via SMS
        //todo uncomment after textlocal or other SMS service template is approved and functional
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.TEXT_LOCAL_SEND_SMS_URL)
//                .queryParam("apikey", envConstants.getTextLocalApiKey())
//                .queryParam("message", Constants.OTP_MESSAGE + otp + ".")
//                .queryParam("sender", Constants.SMS_SENDER)
//                .queryParam("numbers", "91" + requestBody.getPhonenumber());
//
//        var sendResponse = textLocalRestTemplate.getForEntity(
//                builder.toUriString(),
//                TextLocalResponse.class);
//
//        log.info("Sent SMS to " + requestBody.getPhonenumber() + " . Details: " + sendResponse.toString());

        //send otp via Whatsapp
        var whatsappRestTemplate = beanFactory
                .getBean(RestTemplate.class, whatsappMessagingErrorHandler(phoneNumber));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(envConstants.getWhatsappAuthKey());
        var body = Constants.WHATSAPP_REQUEST_BODY.formatted(
                "+91" + phoneNumber,
                envConstants.getWhatsappTemplateName(),
                otp
        );

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        whatsappRestTemplate.exchange(
                envConstants.getWhatsappMessageUrl(),
                HttpMethod.POST,
                entity,
                String.class);

        return otp;
    }
}
