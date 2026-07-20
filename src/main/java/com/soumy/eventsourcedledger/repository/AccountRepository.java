package com.soumy.eventsourcedledger.repository;

import com.soumy.eventsourcedledger.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT COUNT(a) FROM Account a")
    long countAccounts();

}