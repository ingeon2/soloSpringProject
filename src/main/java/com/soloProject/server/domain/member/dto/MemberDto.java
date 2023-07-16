package com.soloProject.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MemberDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Create {

        @NotBlank
        private long memberId;

        @NotBlank
        @Email
        private String name;

        @NotBlank
        private String password;

        @NotBlank
        private String position;

        @NotBlank
        private String email;

        @NotBlank
        private int result;
    }


}
