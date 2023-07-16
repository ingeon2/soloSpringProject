package com.soloProject.server.domain.balance.entity;

import java.math.BigDecimal;

public class Balance {

    private int amount;

    private Balance() {}

    private static class LazyHolder {
        public static final Balance balance = new Balance();
    }

    public static Balance getInstance() {
        return LazyHolder.balance;
    }

    public synchronized void updateBalance(int changeAmount) { //잔고 전체 변경
        amount += changeAmount;
    }

    public int getBalance() {
        return amount;
    }

}
