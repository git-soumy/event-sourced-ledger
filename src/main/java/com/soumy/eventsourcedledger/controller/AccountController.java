package com.soumy.eventsourcedledger.controller;

import com.soumy.eventsourcedledger.dto.CreateAccountRequest;
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
}