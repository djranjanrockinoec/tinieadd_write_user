package com.tinie.otpgen.controllers;

import com.tinie.otpgen.domain.TextLocalResponse;
import com.tinie.otpgen.requests.OTPGenRequest;
import com.tinie.otpgen.utils.Constants;
import com.tinie.otpgen.utils.EnvConstants;
import com.tinie.otpgen.utils.OTPGen;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@Log4j2
public class OTPController {

    private final OTPGen otpGen;
    private final EnvConstants envConstants;
    private final RestTemplate restTemplate;

    @Autowired
    public OTPController(OTPGen otpGen, EnvConstants envConstants, RestTemplate restTemplate) {
        this.otpGen = otpGen;
        this.envConstants = envConstants;
        this.restTemplate = restTemplate;
    }

    /**
     * Generate and send OTP to provided phone number
     * @param httpServletRequest An object of type {@link HttpServletRequest} containing all the information about the request.
     * @param requestBody {@link OTPGenRequest} containing phone number
     * @return A {@link ResponseEntity} whose payload is a {@link java.util.Map}.
     * */
    @PostMapping("otp-manager")
    @Operation(summary = "Verify Token still valid. Do not prepend phone number with international code!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP SENT"),
            @ApiResponse(responseCode = "500", description = "OTP NOT SENT")
    })
    public ResponseEntity<?> verifyToken(HttpServletRequest httpServletRequest,
                                                      @RequestBody OTPGenRequest requestBody) {

        var otp = otpGen.gen6DigitOTP();

        //todo uncomment after textlocal or other SMS service template is approved and functional
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(Constants.TEXT_LOCAL_SEND_SMS_URL)
//                .queryParam("apikey", envConstants.getTextLocalApiKey())
//                .queryParam("message", Constants.OTP_MESSAGE + otp + ".")
//                .queryParam("sender", Constants.SMS_SENDER)
//                .queryParam("numbers", "91" + requestBody.getPhonenumber());
//
//        var sendResponse = restTemplate.getForEntity(
//                builder.toUriString(),
//                TextLocalResponse.class);
//
//        log.info("Sent SMS to " + requestBody.getPhonenumber() + " . Details: " + sendResponse.toString());

        return ResponseEntity.ok(
                Map.of(
                        "phonenumber", requestBody.getPhonenumber(),
                        "OTP", otp,
                        "messagestatus", "OK"
                )
        );
    }
}
