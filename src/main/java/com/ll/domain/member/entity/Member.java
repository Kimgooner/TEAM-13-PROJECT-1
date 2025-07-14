package com.ll.domain.member.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    private String email;
    private String user_id;
    private String password;
    private String name;
    private String address;
    private enum Role {
        ADMIN, USER
    }
}
