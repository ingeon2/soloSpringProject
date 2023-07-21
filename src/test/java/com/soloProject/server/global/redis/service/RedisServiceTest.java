package com.soloProject.server.global.redis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void saveRefreshTokenToRedisTest() {
        //given
        String refreshToken = "sampleRefreshToken";
        long memberId = 123;

        //when
        redisService.saveRefreshTokenToRedis(refreshToken, memberId);

        //then
        String redisKey = "refreshToken:" + String.valueOf(memberId);
        String savedToken = redisTemplate.opsForValue().get(redisKey);
        assertThat(refreshToken).isEqualTo(savedToken);

        //(만료기한체크)
        Long expiration = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        assertThat(expiration).isGreaterThan(0);
        assertThat(expiration).isLessThan(600);
    }

    @Test
    public void getRefreshTokenFromRedisTest() {
        // Given
        String refreshToken = "sampleRefreshToken";
        long memberId = 123;
        String redisKey = "refreshToken:" + String.valueOf(memberId);
        redisTemplate.opsForValue().set(redisKey, refreshToken);

        // When
        String retrievedToken = redisService.getRefreshTokenFromRedis(memberId);

        // Then
        assertThat(refreshToken).isEqualTo(retrievedToken);
    }
}
