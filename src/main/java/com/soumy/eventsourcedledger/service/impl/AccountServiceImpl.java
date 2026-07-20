package com.soumy.eventsourcedledger.service.impl;

import com.soumy.eventsourcedledger.dto.CreateAccountRequest;
import com.soumy.eventsourcedledger.dto.DepositRequest;
import com.soumy.eventsourcedledger.dto.DepositResponse;
import com.soumy.eventsourcedledger.entity.Account;
import com.soumy.eventsourcedledger.entity.LedgerEvent;
import com.soumy.eventsourcedledger.enums.EventType;
import com.soumy.eventsourcedledger.repository.AccountRepository;
import com.soumy.eventsourcedledger.repository.LedgerEventRepository;
import com.soumy.eventsourcedledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LedgerEventRepository ledgerEventRepository;


    @Override
    public Account createAccount(CreateAccountRequest request) {
        long count = accountRepository.countAccounts() + 1;

        String accountNumber = "ACC" + String.format("%06d", count);

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .ownerName(request.getOwnerName())
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }

    @Override
    public DepositResponse deposit(String accountNumber, DepositRequest request) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        LedgerEvent event = LedgerEvent.builder()
                .account(account)
                .eventType(EventType.DEPOSIT)
                .amount(request.getAmount())
                .createdAt(LocalDateTime.now())
                .build();

        ledgerEventRepository.save(event);

        return DepositResponse.builder()
                .message("Deposit successful")
                .accountNumber(account.getAccountNumber())
                .eventType(EventType.DEPOSIT)
                .amount(event.getAmount())
                .createdAt(event.getCreatedAt())
                .build();
    }


}