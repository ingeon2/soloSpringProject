package com.soloProject.server.doamin.member.service;

import com.soloProject.server.domain.member.dto.MemberDto;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@Sql("/sql/member-service-test-data.sql")
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void createMemberTest() {
        //given
        MemberDto.Create memberCreateDto = MemberDto.Create.builder()
                .name("나부장")
                .email("ddd@ddd.com")
                .position("부장")
                .balance(0)
                .password("1111")
                .build();

        //when
        Member result = memberService.createMember(memberCreateDto);

        //then
        assertThat(result.getMemberId()).isNotNull();

    }
}
