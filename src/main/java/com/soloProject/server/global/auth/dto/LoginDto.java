package com.soloProject.server.global.auth.dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private String username;
    private String password;
}
//로그인 인증 정보 역직렬화(Deserialization)를 위한 LoginDTO 클래스