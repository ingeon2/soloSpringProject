package com.soloProject.server.domain.member.service;

import com.soloProject.server.domain.member.dto.MemberDto;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.repository.MemberRepository;
import com.soloProject.server.global.exception.BusinessLogicException;
import com.soloProject.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public Member createMember(MemberDto.Create memberCreateDto) {
        Member member = new Member();
        member.setMemberId(memberCreateDto.getMemberId());
        member.setName(memberCreateDto.getName());
        member.setEmail(memberCreateDto.getEmail());
        member.setPosition(memberCreateDto.getPosition());
        member.setPassword(memberCreateDto.getPassword());

        memberRepository.save(member);
        return member;
    }


    public void updateResult(long memberId, int changeResult) { //어떤 멤버가 얼마만큼의 이득을 냈는지 
        // (product의 buyPrice, sellPrice에 따라 지정)
        Member verifiedMember = findVerifiedMember(memberId);
        int curResult = verifiedMember.getResult();
        verifiedMember.setResult(curResult + changeResult);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

}