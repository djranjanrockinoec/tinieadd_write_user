package com.tinie.otpgen.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.DecimalFormat;

@Component
public class OTPGen {

    private final SecureRandom random;

    public OTPGen() {
        random = new SecureRandom();
    }

    public String gen6DigitOTP() {

        return new DecimalFormat("000000")
                .format(random.nextInt(1000000));
    }
}
