package com.soloProject.server.global.chatGPT.controller;


import com.soloProject.server.global.chatGPT.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@Transactional
@RequiredArgsConstructor
@RequestMapping("/chatGPT")
public class ChatController {
    private final static String GPT_DEFAULT_URL = "/chatGPT";

    private final ChatService chatService;

    @PostMapping
    public String chatting (@RequestBody String question) {
        return chatService.getChatting(question);
    }
}
