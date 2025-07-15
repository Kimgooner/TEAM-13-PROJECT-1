package com.ll.domain.member.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Column(unique = true)
    private String email;
    // String user_id;
    private String password;
    private String name;
    private String address;
    private Role role;

    public enum Role {
        ADMIN, USER
    }

    @Builder
    public Member(String email, String password, String name, String address, Role role) {
        this.email = email; // id로 사용 예정
        // this.user_id = user_id; // 사용처 없음. identity 키의 경우 BaseEntity에서 이미 선언됨.
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }
}
