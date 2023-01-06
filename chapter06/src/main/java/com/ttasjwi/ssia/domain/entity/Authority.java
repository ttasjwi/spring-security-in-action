package com.ttasjwi.ssia.domain.entity;

import com.ttasjwi.ssia.domain.vo.AuthorityId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Authority {

    private AuthorityId id;
    private String name;
    private Member member;

    @Builder
    public Authority(AuthorityId id, String name, Member member) {
        this.id = id;
        this.name = name;
        this.member = member;
    }
}
