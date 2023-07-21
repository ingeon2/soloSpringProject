package com.soloProject.server.global.chatGPT.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatgptService chatgptService;

    @MockBean
    private ChatService chatService;

    @Test
    public void testGetChatting() {
        //given
        String question = "질문";
        String responseFromGpt = null;


        //when
        String result = chatService.getChatting(question);

        //then
        assertThat(responseFromGpt).isEqualTo(result);

    }
}