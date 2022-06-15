package com.tinie.otpgen.exceptions;

import lombok.Getter;

public class SMSFailedException extends RuntimeException {
    @Getter
    private final long phoneNumber;

    public SMSFailedException(String messageStatus, long phoneNumber) {
        super(messageStatus);
        this.phoneNumber = phoneNumber;
    }
}
