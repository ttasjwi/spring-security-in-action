package com.ttasjwi.ssia.member.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MemberId {

    private Long id;

    public MemberId(Long id) {
        this.id = id;
    }
}
