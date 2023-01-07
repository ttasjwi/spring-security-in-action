package com.ttasjwi.ssia.domain.entity;

import com.ttasjwi.ssia.domain.vo.EncryptionAlgorithm;
import com.ttasjwi.ssia.domain.vo.MemberId;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Member {

    private MemberId id;
    private String name;
    private String password;
    private EncryptionAlgorithm algorithm;
    private final List<Authority> authorities = new ArrayList<>();

    @Builder
    public Member(MemberId id, String name, String password, EncryptionAlgorithm algorithm, List<Authority> authorities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.algorithm = algorithm;
        this.authorities.addAll(authorities);
    }
}
