package com.ttasjwi.ssia.adapter.output.persistence.mapper;

import com.ttasjwi.ssia.adapter.output.persistence.data.AuthorityData;
import com.ttasjwi.ssia.adapter.output.persistence.data.MemberData;
import com.ttasjwi.ssia.domain.entity.Authority;
import com.ttasjwi.ssia.domain.entity.Member;
import com.ttasjwi.ssia.domain.vo.AuthorityId;
import com.ttasjwi.ssia.domain.vo.EncryptionAlgorithm;
import com.ttasjwi.ssia.domain.vo.MemberId;
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
