package com.tinie.otpgen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TextLocalResponse(@JsonProperty("num_messages") int numMessages, int cost, String status, int balance) { }
