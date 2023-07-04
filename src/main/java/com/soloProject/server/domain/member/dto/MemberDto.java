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
        private String name;

        @NotBlank
        private String password;


        private String position;

    }


}
