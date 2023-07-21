package com.soloProject.server.global.redis.controller;

import com.soloProject.server.global.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RedisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisService redisService;

    @Test
    public void GetRefreshTokenTest() throws Exception {
        //given
        long memberId = 123;
        String refreshToken = "sampleRefreshToken";

        //when
        Mockito.when(redisService.getRefreshTokenFromRedis(memberId)).thenReturn(refreshToken);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/token/refreshToken/{member-id}", memberId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
