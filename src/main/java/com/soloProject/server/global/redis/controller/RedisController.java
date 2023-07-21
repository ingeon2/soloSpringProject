package com.soloProject.server.global.redis.controller;

import com.soloProject.server.global.dto.SingleResponseDto;
import com.soloProject.server.global.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/token")
@Api(tags = {"Redis API Test"})
public class RedisController {

    @Autowired
    private final RedisService redisService;

    @GetMapping("/refreshToken/{member-id}")
    @ApiOperation(value = "토큰", notes = "RefreshToken 얻어오는 API")
    @ApiImplicitParam(name = "member-id", value = "멤버 아이디")
    public ResponseEntity getRefreshToken(@PathVariable("member-id") @Positive long memberId) {
        String refreshTokenFromRedis = redisService.getRefreshTokenFromRedis(memberId);
        SingleResponseDto<String> response = new SingleResponseDto<>(refreshTokenFromRedis);
        
        return ResponseEntity.ok(response);
    }
}
