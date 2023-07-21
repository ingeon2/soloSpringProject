package com.soloProject.server.domain.member.controller;

import com.soloProject.server.domain.member.dto.MemberDto;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.service.MemberService;
import com.soloProject.server.global.utils.UriCreator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"멤버 API Test"})
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";

    @Autowired
    private final MemberService memberService;

    @PostMapping
    @ApiOperation(value = "멤버", notes = "멤버 등록 API")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Create memberPostDto) {
        Member createMember = memberService.createMember(memberPostDto);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createMember.getMemberId());
        return ResponseEntity.created(location).build();
    }




}
