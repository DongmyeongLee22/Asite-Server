package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;



}
