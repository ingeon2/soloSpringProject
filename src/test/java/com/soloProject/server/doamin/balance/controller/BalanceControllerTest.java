package com.soloProject.server.doamin.balance.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.google.gson.Gson;
import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private BalanceService balanceService;

    @Test
    void postBalanceTest() throws Exception {

        // given
        Balance balance = new Balance();
        given(balanceService.createBalance(Mockito.any(Integer.class)))
                .willReturn(balance);
        //Stubbing은 테스트를 위해서 Mock 객체가 항상 일정한 동작을 하도록 지정하는 것
        //여기에서는 MockBalanceService(가칭)의 createBalance() 메서드가 항상 동일한 balance 객체를 리턴


        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/balance")
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/balance"))));
    }
}
