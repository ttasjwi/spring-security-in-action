package com.ttasjwi.ssia.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AuthorityId {

    private Long id;

    public AuthorityId(Long id) {
        this.id = id;
    }
}
