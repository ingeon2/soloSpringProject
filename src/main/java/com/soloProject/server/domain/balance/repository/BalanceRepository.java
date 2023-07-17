package com.soloProject.server.domain.balance.repository;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findById (long id);
}
