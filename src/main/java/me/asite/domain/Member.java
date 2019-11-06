package me.asite.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String schoolID;

    private String password;

    private String name;

    private String major;

    private MemberType memberType;

    @Builder
    public Member(String schoolID, String password, String name, String major, MemberType memberType) {
        this.schoolID = schoolID;
        this.password = password;
        this.name = name;
        this.major = major;
        this.memberType = memberType;
    }
}
