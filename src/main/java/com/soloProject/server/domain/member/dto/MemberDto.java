package com.soloProject.server.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
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
        @ApiModelProperty(example = "사람 이름")
        private String name;

        @NotBlank
        @ApiModelProperty(example = "비밀번호")
        private String password;

        @NotBlank
        @ApiModelProperty(example = "직책")
        private String position;

        @NotBlank
        @Email
        @ApiModelProperty(example = "이메일")
        private String email;

        @ApiModelProperty(example = "멤버가 실현한 이익")
        private int result;
    }


}
