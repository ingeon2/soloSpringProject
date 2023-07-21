package com.soloProject.server.global.chatGPT.controller;

import com.soloProject.server.global.chatGPT.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private ChatController chatController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
    }

    @Test
    public void testChatting() throws Exception {

        //given
        String question = "질문";
        String responseFromService = "대답";

        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/chatGPT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(question))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
