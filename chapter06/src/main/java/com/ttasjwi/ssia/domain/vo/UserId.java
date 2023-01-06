package com.ttasjwi.ssia.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserId {

    private Long id;

    public UserId(Long id) {
        this.id = id;
    }
}
