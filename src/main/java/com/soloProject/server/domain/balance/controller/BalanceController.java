package com.soloProject.server.domain.balance.controller;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.service.BalanceService;
import com.soloProject.server.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {
    private final static String BALANCE_DEFAULT_URL = "/balance";

    @Autowired
    private final BalanceService balanceService;

    @PostMapping
    public ResponseEntity postBalance() {
        Balance createBalance = balanceService.createBalance(100000000);
        URI location = UriCreator.createUri(BALANCE_DEFAULT_URL, createBalance.getAmount());
        return ResponseEntity.created(location).build();
    }

}
