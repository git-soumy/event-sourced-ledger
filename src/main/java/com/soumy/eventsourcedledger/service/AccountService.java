package com.soumy.eventsourcedledger.service;

import com.soumy.eventsourcedledger.dto.CreateAccountRequest;
import com.soumy.eventsourcedledger.dto.DepositRequest;
import com.soumy.eventsourcedledger.dto.DepositResponse;
import com.soumy.eventsourcedledger.entity.Account;

public interface AccountService {

    Account createAccount(CreateAccountRequest request);

    DepositResponse deposit(String accountNumber,
                            DepositRequest request);

}