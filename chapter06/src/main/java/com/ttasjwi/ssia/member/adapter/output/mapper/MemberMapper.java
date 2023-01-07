package com.ttasjwi.ssia.member.adapter.output.mapper;

import com.ttasjwi.ssia.member.adapter.output.data.AuthorityData;
import com.ttasjwi.ssia.member.adapter.output.data.MemberData;
import com.ttasjwi.ssia.member.domain.entity.Authority;
import com.ttasjwi.ssia.member.domain.entity.Member;
import com.ttasjwi.ssia.member.domain.vo.AuthorityId;
import com.ttasjwi.ssia.member.domain.vo.EncryptionAlgorithm;
import com.ttasjwi.ssia.member.domain.vo.MemberId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public Member mapToDomainEntity(MemberData memberData) {
        Member members = Member.builder()
                .id(new MemberId(memberData.getId()))
                .name(memberData.getName())
                .password(memberData.getPassword())
                .algorithm(EncryptionAlgorithm.valueOf(memberData.getAlgorithm().name()))
                .build();

        List<Authority> authorities = mapToDomainEntities(memberData.getAuthorities());
        members.initAuthorities(authorities);

        return members;
    }

    private List<Authority> mapToDomainEntities(List<AuthorityData> authorities) {
        return authorities.stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    private Authority mapToDomainEntity(AuthorityData data) {
        return Authority.builder()
                .id(new AuthorityId(data.getId()))
                .name(data.getName())
                .build();
    }

}
