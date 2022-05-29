package com.tinie.otpgen.domain;

import com.tinie.otpgen.exceptions.SMSFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {
    /**
     * Handle thrown {@link SMSFailedException} and return a {@link ResponseEntity} with status code of {@literal 400}
     * @param e Instance of {@link SMSFailedException} thrown
     * @return Instance of {@link ResponseEntity} containing {@literal phonenumber} and {@literal messagestatus}
     */
    @ExceptionHandler(value = SMSFailedException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> registerUserHandler(SMSFailedException e){
        return ResponseEntity.badRequest().body(
                Map.of("phonenumber", e.getPhoneNumber(),
                        "messagestatus", e.getMessage())
        );
    }
}
