package com.ll.domain.user.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    private String email;
    private String user_id;
    private String password;
    private String name;
    private String address;
    private enum Role {
        ADMIN, USER
    }
}
