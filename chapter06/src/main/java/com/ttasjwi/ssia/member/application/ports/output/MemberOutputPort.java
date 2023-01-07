package com.ttasjwi.ssia.member.application.ports.output;

import com.ttasjwi.ssia.member.domain.entity.Member;

public interface MemberOutputPort {

    Member findByNameWithAuthorities(String name);
}
