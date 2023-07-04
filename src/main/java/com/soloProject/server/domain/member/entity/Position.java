package com.soloProject.server.domain.member.entity;

import lombok.Getter;

@Getter
public enum Position { //회원등록시 필요한 질문답변에서 질문

    POSITION1("부장"),
    POSITION2("과장"),
    POSITION3("사원");


    private final String value;
    private Position(String value) {
        this.value = value;
    }

    public static Position fromString(String value) {
        for(Position position : Position.values()) {
            if(position.value.equalsIgnoreCase(value)) return position;
        }

        throw new IllegalArgumentException(value + "는, 존재하지 않는 직책입니다.");
    }

}

