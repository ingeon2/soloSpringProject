package com.soloProject.server.global.chatGPT.controller;


import com.soloProject.server.global.chatGPT.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"챗 GPT API Test"})
public class ChatController {
    private final static String GPT_DEFAULT_URL = "/chatGPT";

    private final ChatService chatService;

    @PostMapping
    @ApiOperation(value = "질문", notes = "챗 GPT 질문 등록 API")
    @ApiImplicitParam(name = "question", value = "등록할 질문")
    public String chatting (@RequestBody String question) {
        return chatService.getChatting(question);
    }
}
