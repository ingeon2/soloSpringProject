package com.soloProject.server.doamin.member.repository;

import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.repository.MemberRepository;
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
@Sql("/sql/member-repository-test-data.sql")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("memberRepository의 findByEmail 테스트")
    void findByEmailTest() {
        //given


        //when
        Optional<Member> result1 = memberRepository.findByEmail("dlsrjsdl@naver.com");
        Optional<Member> result2 = memberRepository.findByEmail("notPresentMember");

        //then
        assertThat(result1.isPresent()).isTrue();
        assertThat(result2.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("memberRepository의 findById 테스트")
    void findByIdTest() {
        //given


        //when
        Optional<Member> result1 = memberRepository.findById(1);
        Optional<Member> result2 = memberRepository.findById(2);

        //then
        assertThat(result1.isPresent()).isTrue();
        assertThat(result2.isEmpty()).isTrue();
    }
}
