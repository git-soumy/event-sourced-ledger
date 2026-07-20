package com.soumy.eventsourcedledger.service;

import com.soumy.eventsourcedledger.dto.*;
import com.soumy.eventsourcedledger.entity.Account;

public interface AccountService {

    Account createAccount(CreateAccountRequest request);

    DepositResponse deposit(String accountNumber,
                            DepositRequest request);

    WithdrawResponse withdraw(String accountNumber,
                              WithdrawRequest request);

}