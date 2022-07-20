package com.tinie.otpgen.utils;

public class Constants {

    public static final String TEXT_LOCAL_SEND_SMS_URL = "https://api.textlocal.in/send/";
//    public static final String SMS_SENDER = "TINIE";
//    public static final String OTP_MESSAGE = "TINIE OTP CODE: ";
    public static final String SMS_SENDER = "600010";
    public static final String OTP_MESSAGE = "Hi there, thank you for sending your first test message from Textlocal. Get 20% off today with our code: ";
    public static String WHATSAPP_REQUEST_BODY = """
            {
                "messaging_product": "whatsapp",
                "to": "%s",
                "type": "template",
                "template": {
                    "name": "%s",
                    "language": {
                        "code": "en_US"
                    },
                    "components": [
                        {
                            "type": "body",
                            "parameters": [
                                {
                                    "type": "text",
                                    "text": "%s"
                                }
                            ]
                        }
                    ]
                }
            }
            """;
}
