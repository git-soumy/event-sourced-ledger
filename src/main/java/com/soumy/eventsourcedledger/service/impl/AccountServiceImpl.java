package com.soumy.eventsourcedledger.service.impl;

import com.soumy.eventsourcedledger.dto.CreateAccountRequest;
import com.soumy.eventsourcedledger.entity.Account;
import com.soumy.eventsourcedledger.repository.AccountRepository;
import com.soumy.eventsourcedledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(CreateAccountRequest request) {

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString())
                .ownerName(request.getOwnerName())
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }
}