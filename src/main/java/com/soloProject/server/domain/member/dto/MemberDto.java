package com.soloProject.server.domain.member.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
