package com.tinie.otpgen.exceptions;

import lombok.Getter;

public class OTPSendFailedException extends RuntimeException{
    @Getter
    private final long phoneNumber;
    public OTPSendFailedException(String message, long phoneNumber){
        super(message);
        this.phoneNumber = phoneNumber;
    }
}
