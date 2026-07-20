package com.soumy.eventsourcedledger.dto;

import com.soumy.eventsourcedledger.enums.EventType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class WithdrawResponse {

    private String message;

    private String accountNumber;

    private EventType eventType;

    private BigDecimal amount;

    private LocalDateTime createdAt;
}