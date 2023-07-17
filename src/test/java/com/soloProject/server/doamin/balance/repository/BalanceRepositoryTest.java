package com.soloProject.server.doamin.balance.repository;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.repository.BalanceRepository;
import com.soloProject.server.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/balance-repository-test-data.sql")
public class BalanceRepositoryTest {

    @Autowired
    BalanceRepository balanceRepository;

    @Test
    @DisplayName("balanceRepository getBalance 테스트")
    void getBalanceTest() {
        //given

        //when
        Optional<Balance> result = balanceRepository.findById(1L);

        //then
        assertThat(result.isPresent()).isTrue();
    }
}
