package com.soloProject.server.domain.balance.service;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BalanceService {

    @Autowired
    private final BalanceRepository balanceRepository;

    public Balance createBalance(int initialAmount) {
        Balance balance = new Balance();
        balance.setBalance_id(1L);
        balance.setAmount(initialAmount);
        balanceRepository.save(balance);

        return balance;
    }

    //balance존재 여부 체크
    public int getBalance() {
        Balance balance = balanceRepository.findById(1L).orElse(null);
        return (balance != null) ? balance.getAmount() : 0;
    }

    public void updateBalance(int changeAmount) {
        Balance balance = balanceRepository.findById(1L).orElse(null);
        if (balance != null) {
            int newAmount = balance.getAmount() + changeAmount;
            balance.setAmount(newAmount);
            balanceRepository.save(balance);
        }
    }
}
