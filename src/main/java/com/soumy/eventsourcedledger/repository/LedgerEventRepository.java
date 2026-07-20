package com.soumy.eventsourcedledger.repository;

import com.soumy.eventsourcedledger.entity.Account;
import com.soumy.eventsourcedledger.entity.LedgerEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerEventRepository extends JpaRepository<LedgerEvent, Long> {

    List<LedgerEvent> findByAccount(Account account);

}