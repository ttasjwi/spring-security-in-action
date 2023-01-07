package com.ttasjwi.ssia.adapter.output.persistence;

import com.ttasjwi.ssia.adapter.output.persistence.data.MemberData;
import com.ttasjwi.ssia.adapter.output.persistence.mapper.MemberMapper;
import com.ttasjwi.ssia.adapter.output.persistence.repository.MemberRepository;
import com.ttasjwi.ssia.application.ports.output.MemberOutputPort;
import com.ttasjwi.ssia.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberOutputPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member findByNameWithAuthorities(String name) {
        MemberData memberData = memberRepository.findByNameWithAuthorities(name)
                .orElseThrow(() -> new UsernameNotFoundException("Problem during authentication!"));

        return memberMapper.mapToDomainEntity(memberData);
    }
}
