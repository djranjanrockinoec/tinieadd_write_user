package com.tinie.otpgen.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class OTPGenRequest {

    @Schema(required = true, description = "The User's phone number")
    @Min(1)
    private long phonenumber;
}
