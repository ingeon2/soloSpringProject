package com.soloProject.server.doamin.balance.service;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/balance-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class BalanceServiceTest {
    @Autowired
    BalanceService balanceService;

    @Test
    void createBalanceTest() {
        //given
        int initialAmount = 0;

        //when
        Balance result = balanceService.createBalance(initialAmount);

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void getBalanceTest() {
        //given

        //when
        int balance = balanceService.getBalance();

        //then
        assertThat(balance).isEqualTo(100);
    }

    @Test
    void updateBalanceTest() {
        //given

        //when
        balanceService.updateBalance(-100);
        int balance = balanceService.getBalance();

        //then
        assertThat(balance).isEqualTo(0);

    }

}
