package com.soloProject.server.domain.member.controller;

import com.soloProject.server.domain.member.dto.MemberDto;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.service.MemberService;
import com.soloProject.server.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";

    @Autowired
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Create memberPostDto) {
        Member createMember = memberService.createMember(memberPostDto);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createMember.getMemberId());
        return ResponseEntity.created(location).build();
    }




}
