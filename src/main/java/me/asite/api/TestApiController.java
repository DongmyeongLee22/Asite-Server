package me.asite.api;

import lombok.RequiredArgsConstructor;
import me.asite.repository.MemberRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestApiController {

    private final MemberRepository memberRepository;

    @PostMapping("/api/test")
    public void joinMember(@RequestBody MemberJoinRequestDto dto){
        memberRepository.save(dto.toEntity());
    }

}
