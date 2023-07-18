package com.soloProject.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Create {

        @NotBlank
        private String name;

        @NotBlank
        private String password;

        @NotBlank
        private String position;

        @NotBlank
        @Email
        private String email;

        private int result;
    }


}
