package com.ll.domain.member.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    private String email;
    private String user_id;
    private String password;
    private String name;
    private String address;
    private Role role;

    public enum Role {
        ADMIN, USER
    }

    @Builder
    public Member(String email, String user_id, String password, String name, String address, Role role) {
        this.email = email;
        this.user_id = user_id;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
    }
}
