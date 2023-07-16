package com.soloProject.server.doamin.member.service;

import com.soloProject.server.domain.member.dto.MemberDto;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.service.MemberService;
import com.soloProject.server.global.exception.BusinessLogicException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/member-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Test
    void createMemberTest() {
        //given
        MemberDto.Create memberCreateDto = MemberDto.Create.builder()
                .memberId(2L)
                .name("나부장")
                .email("ddd@ddd.com")
                .position("부장")
                .result(0)
                .password("1111")
                .build();

        //when
        Member result = memberService.createMember(memberCreateDto);

        //then
        assertThat(result.getMemberId()).isNotNull();
    }


    @Test
    void updateResultTest() {
        //given
        long memberId = 1;
        int changeReslut = 100;

        //when
        memberService.updateResult(memberId, changeReslut);
        Member member = memberService.findVerifiedMember(memberId);

        //then
        assertThat(member.getResult()).isEqualTo(100);

    }

}
