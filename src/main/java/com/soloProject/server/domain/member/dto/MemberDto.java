package com.soloProject.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MemberDto {

    @Getter
    @AllArgsConstructor
    public static class Post { //mem001

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String passwordConfirm;
        
        @NotBlank //주민번호
        private String RRN;

        @NotBlank(message = "닉네임은 공백이 아니어야 합니다")
        @Size(max = 10, message = "닉네임은 10글자 이하여야 합니다")
        private String nickname;

        private String question;
        private String answer;

    }


}
