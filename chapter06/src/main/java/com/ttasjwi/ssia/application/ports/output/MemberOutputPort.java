package com.ttasjwi.ssia.application.ports.output;

import com.ttasjwi.ssia.domain.entity.Member;

public interface MemberOutputPort {

    Member findByNameWithAuthorities(String name);
}
