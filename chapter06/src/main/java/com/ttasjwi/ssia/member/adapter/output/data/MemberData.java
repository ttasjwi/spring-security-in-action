package com.ttasjwi.ssia.member.adapter.output.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class MemberData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private PasswordEncryptionAlgorithmData algorithm;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private final List<AuthorityData> authorities = new ArrayList<>();

    @Builder
    public MemberData(Long id, String name, String password,
                      PasswordEncryptionAlgorithmData algorithm, List<AuthorityData> authorities) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.algorithm = algorithm;
        this.authorities.addAll(authorities);
    }
}
