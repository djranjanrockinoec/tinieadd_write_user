package com.tinie.otpgen.controllers;

import com.tinie.otpgen.requests.OTPGenRequest;
import com.tinie.otpgen.services.OTPService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@Log4j2
public class OTPController {

    private final OTPService otpService;

    @Autowired
    public OTPController(OTPService otpService) {
        this.otpService = otpService;
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
    public ResponseEntity<?> genOTP(HttpServletRequest httpServletRequest,
                                                      @RequestBody OTPGenRequest requestBody) {

        var otp = otpService.sendOTP(requestBody.getPhonenumber());

        return ResponseEntity.ok(
                Map.of(
                        "phonenumber", requestBody.getPhonenumber(),
                        "OTP", otp,
                        "messagestatus", "OK"
                )
        );
    }
}
