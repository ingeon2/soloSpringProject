package com.soloProject.server.domain.member.service;

import com.soloProject.server.domain.member.dto.MemberDto;
import com.soloProject.server.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    public void createMember(MemberDto.Post memberPostDto) {
        //d
    }

}