package com.soumy.eventsourcedledger.controller;

import com.soumy.eventsourcedledger.dto.CreateAccountRequest;
import com.soumy.eventsourcedledger.dto.DepositRequest;
import com.soumy.eventsourcedledger.dto.DepositResponse;
import com.soumy.eventsourcedledger.entity.Account;
import com.soumy.eventsourcedledger.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {

        return accountService.createAccount(request);
    }

    @PostMapping("/{accountNumber}/deposit")
    public DepositResponse deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        return accountService.deposit(accountNumber, request);
    }
}