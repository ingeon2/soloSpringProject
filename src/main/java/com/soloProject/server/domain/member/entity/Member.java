package com.soloProject.server.domain.member.entity;

import com.soloProject.server.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String password;

    @Column
    private String position;

    @Column
    private int result;


    @ElementCollection(fetch = FetchType.EAGER) //db 왔다갔다하지말고 한번에 다 꺼내와라
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @org.hibernate.annotations.ForeignKey(name = "none") // 외래 키 제약 조건 무시
    private List<String> roles = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();




}