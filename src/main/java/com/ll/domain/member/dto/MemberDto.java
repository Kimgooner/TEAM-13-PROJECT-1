package com.ll.domain.member.dto;

import com.ll.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {
    @Getter
    @Setter
    public static class JoinRequest {
        private String email;
        private String user_id;
        private String password;
        private String name;
        private String address;
    }

    @Getter
    public static class Response {
        private int id;
        private String email;
        private String user_id;
        private String name;
        private String address;

        public Response(Member member){
            this.id = member.getId();
            this.email = member.getEmail();
            this.user_id = member.getUser_id();
            this.name = member.getName();
            this.address = member.getAddress();
        }
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }
}
