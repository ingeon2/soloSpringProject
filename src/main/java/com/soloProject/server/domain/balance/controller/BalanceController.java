package com.soloProject.server.domain.balance.controller;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.service.BalanceService;
import com.soloProject.server.global.utils.UriCreator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/balance")
@Api(tags = {"잔액 API Test"})  // Swagger 최상단 Controller 명칭
public class BalanceController {
    private final static String BALANCE_DEFAULT_URL = "/balance";

    @Autowired
    private final BalanceService balanceService;

    @PostMapping("/{amount}")
    @ApiOperation(value = "잔액 등록", notes = "재고 등록 API, 잔액은 단 한번만 등록") // Swagger에 사용하는 API에 대한 간단 설명
    public ResponseEntity postBalance(@Positive @PathVariable("amount") int amount) {
        int existingBalance = balanceService.getBalance();
        //getBalance -> 있으면 잔액을 반환하고, 없으면 0을 반환
        if(existingBalance == 0) { //지금 잔액 객체 자체가 없다면 생성
            Balance createBalance = balanceService.createBalance(amount);
            URI location = UriCreator.createUri(BALANCE_DEFAULT_URL, createBalance.getAmount());
            return ResponseEntity.created(location).build();
        }

        //있다면 생성말고 존재한다고 return
        return ResponseEntity.badRequest().body("잔액 이미 존재합니다");

    }

}
