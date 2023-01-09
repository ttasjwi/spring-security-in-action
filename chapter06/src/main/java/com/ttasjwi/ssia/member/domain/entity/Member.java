package com.ttasjwi.ssia.member.domain.entity;

import com.ttasjwi.ssia.member.domain.vo.MemberId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Member {

    private MemberId id;
    private String name;
    private String password;
    private final List<Authority> authorities = new ArrayList<>();

    @Builder
    public Member(MemberId id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void initAuthorities(List<Authority> authorities) {
        authorities.forEach(this::addAuthority);
    }

    public void addAuthority(Authority authority) {
        authorities.add(authority);
        authority.initMember(this);
    }
}
