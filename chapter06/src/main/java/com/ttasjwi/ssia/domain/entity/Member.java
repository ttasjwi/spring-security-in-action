package com.ttasjwi.ssia.domain.entity;

import com.ttasjwi.ssia.domain.vo.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Member {

    private UserId id;
    private String name;
    private String password;
    private List<Authority> authorities;

    @Builder
    public Member(UserId id, String name, String password, List<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }
}
