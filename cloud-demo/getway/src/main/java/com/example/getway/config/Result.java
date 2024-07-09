package com.example.getway.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {

    private int code;

    private String msg;
}