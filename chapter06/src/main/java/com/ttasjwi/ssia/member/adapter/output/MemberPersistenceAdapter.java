package com.ttasjwi.ssia.member.adapter.output;

import com.ttasjwi.ssia.member.adapter.output.data.MemberData;
import com.ttasjwi.ssia.member.adapter.output.mapper.MemberMapper;
import com.ttasjwi.ssia.member.adapter.output.repository.MemberRepository;
import com.ttasjwi.ssia.member.application.ports.output.MemberOutputPort;
import com.ttasjwi.ssia.member.domain.entity.Member;
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
