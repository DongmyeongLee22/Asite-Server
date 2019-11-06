package me.asite.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.asite.domain.Member;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDto {

    private String name;
    private String schoolID;
    private String password;

    public Member toEntity() {

        return Member.builder()
                .name(name)
                .schoolID(schoolID)
                .password(password)
                .build();

    }
}
