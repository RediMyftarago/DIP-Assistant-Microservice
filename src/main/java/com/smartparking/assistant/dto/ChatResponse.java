package com.smartparking.assistant.dto;

public record ChatResponse(

        String answer,
        //Nga vjen pergjigja faq apo gpt
        String source

) {}
