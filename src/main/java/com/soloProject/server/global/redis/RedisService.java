package com.soloProject.server.global.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // refreshToken을 Redis에 저장
    public void saveRefreshTokenToRedis(String refreshToken, long memberId) {
        //키 생성
        String redisKey = "refreshToken:" + String.valueOf(memberId);
        
        //키와 밸류를 redis에 저장
        redisTemplate.opsForValue().set(redisKey, refreshToken);
        
        //만료시간 설정해주기 (10분 만료로 설정)
        Duration expiration = Duration.ofMinutes(10);
        redisTemplate.expire(redisKey, expiration);
    }


    // 사용자 ID를 통해 Redis에서 refreshToken을 가져와서 확인
    //클라이언트가 보내준 refreshToken과 Redis에 저장된 refreshToken이 일치하다면, accessToken과 남은 만료 기간에 따라 refreshToken까지 새로 고쳐서 재발급
    public String getRefreshTokenFromRedis(long memberId) {
        String redisKey = "refreshToken:" + String.valueOf(memberId);
        return redisTemplate.opsForValue().get(redisKey);
    }


}
