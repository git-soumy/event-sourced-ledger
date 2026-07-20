package com.soumy.eventsourcedledger.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(BigDecimal balance, BigDecimal requestedAmount) {
        super("Insufficient balance. Current balance: " + balance +
                ", Requested amount: " + requestedAmount);
    }
}