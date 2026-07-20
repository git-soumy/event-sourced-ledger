package com.soumy.eventsourcedledger.service;

import com.soumy.eventsourcedledger.dto.CreateAccountRequest;
import com.soumy.eventsourcedledger.entity.Account;

public interface AccountService {

    Account createAccount(CreateAccountRequest request);

    Account deposit(String accountNumber,
                    DepositRequest request);

}