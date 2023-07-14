package com.soloProject.server.doamin.product.repository;


import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/product-repository-test-data.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    void findByNameTest() {
        //given


        //when
        Optional<Product> result1 = productRepository.findByName("NT-960KA19");
        Optional<Product> result2 = productRepository.findByName("notPresentProduct");

        //then
        assertThat(result1.isPresent()).isTrue();
        assertThat(result2.isEmpty()).isTrue();
    }

}
