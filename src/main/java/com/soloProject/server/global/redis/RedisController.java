package com.soloProject.server.global.redis;

import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.global.dto.SingleResponseDto;
import com.soloProject.server.global.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/token")
public class RedisController {

    @Autowired
    private final RedisService redisService;

    @GetMapping("/refreshToken/{member-id}")
    public ResponseEntity getRefreshToken(@PathVariable("member-id") @Positive long memberId) {
        String refreshTokenFromRedis = redisService.getRefreshTokenFromRedis(memberId);
        if(refreshTokenFromRedis == null) System.out.println("널");
        SingleResponseDto<String> response = new SingleResponseDto<>(refreshTokenFromRedis);
        
        return ResponseEntity.ok(response);
    }
}
