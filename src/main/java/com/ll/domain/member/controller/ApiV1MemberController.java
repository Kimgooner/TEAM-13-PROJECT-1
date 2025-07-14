package com.ll.domain.member.controller;

import com.ll.domain.member.dto.MemberDto;
import com.ll.domain.member.entity.Member;
import com.ll.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDto.Response> join(@RequestBody MemberDto.JoinRequest dto){ // 회원 가입
        Member member = memberService.addUserMember(
                dto.getEmail(),
                dto.getUser_id(),
                dto.getPassword(),
                dto.getName(),
                dto.getAddress()
        );

        MemberDto.Response response = new MemberDto.Response(member);
        return ResponseEntity.created(URI.create("/api/v1/members/" + member.getId())).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.LoginRequest dto){ // 로그인
        Optional<Member> memberOptional = memberService.login(dto.getEmail(), dto.getPassword());

        if(memberOptional.isEmpty()){
            return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return ResponseEntity.ok(new MemberDto.Response(memberOptional.get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto.Response> findById(@PathVariable Integer id){ // id로 멤버 찾기
        return memberService.findById(id)
                .map(member -> ResponseEntity.ok(new MemberDto.Response(member)))
                .orElse(ResponseEntity.notFound().build());
    }
}
