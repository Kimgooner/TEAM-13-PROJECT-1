package com.ll.domain.member.service;

import com.ll.domain.member.entity.Member;
import com.ll.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public long count() {return memberRepository.count();}
    public Member addUserMember(String email, String user_id, String password, String name, String address){
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.builder()
                .email(email)
                .user_id(user_id)
                .password(encodedPassword)
                .name(name)
                .address(address)
                .build();

        return memberRepository.save(member);
    }

    public Optional<Member> findById(Integer id){return memberRepository.findById(id);}
    public List<Member> findAll(){return memberRepository.findAll();}

    public Optional<Member> findByEmail(String email){return memberRepository.findByEmail(email);}
    public Optional<Member> findByName(String name){return  memberRepository.findByName(name);}

    public void deleteById(Integer id){memberRepository.deleteById(id);}

    public void flush(){memberRepository.flush();}
}
