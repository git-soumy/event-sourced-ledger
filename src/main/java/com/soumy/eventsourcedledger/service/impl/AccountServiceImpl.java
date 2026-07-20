package com.soumy.eventsourcedledger.service.impl;

import com.soumy.eventsourcedledger.dto.*;
import com.soumy.eventsourcedledger.entity.Account;
import com.soumy.eventsourcedledger.entity.LedgerEvent;
import com.soumy.eventsourcedledger.enums.EventType;
import com.soumy.eventsourcedledger.exception.AccountNotFoundException;
import com.soumy.eventsourcedledger.exception.InsufficientBalanceException;
import com.soumy.eventsourcedledger.repository.AccountRepository;
import com.soumy.eventsourcedledger.repository.LedgerEventRepository;
import com.soumy.eventsourcedledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

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

    @Override
    public WithdrawResponse withdraw(String accountNumber,
                                     WithdrawRequest request) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        BigDecimal currentBalance = calculateBalance(account);

        if (currentBalance.compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(currentBalance, request.getAmount());
        }

        LedgerEvent event = LedgerEvent.builder()
                .account(account)
                .eventType(EventType.WITHDRAW)
                .amount(request.getAmount())
                .createdAt(LocalDateTime.now())
                .build();

        ledgerEventRepository.save(event);

        return WithdrawResponse.builder()
                .message("Withdrawal successful")
                .accountNumber(account.getAccountNumber())
                .eventType(EventType.WITHDRAW)
                .amount(event.getAmount())
                .createdAt(event.getCreatedAt())
                .build();
    }

    private BigDecimal calculateBalance(Account account) {

        List<LedgerEvent> events = ledgerEventRepository.findByAccount(account);

        BigDecimal balance = BigDecimal.ZERO;

        for (LedgerEvent event : events) {

            switch (event.getEventType()) {

                case DEPOSIT, TRANSFER_IN ->
                        balance = balance.add(event.getAmount());

                case WITHDRAW, TRANSFER_OUT ->
                        balance = balance.subtract(event.getAmount());
            }
        }

        return balance;
    }


}