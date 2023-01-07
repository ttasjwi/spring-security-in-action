package com.ttasjwi.ssia.adapter.output.persistence.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "authority")
public class AuthorityData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    private String name;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private MemberData member;

    @Builder
    public AuthorityData(Long id, String name, MemberData member) {
        this.id = id;
        this.name = name;
        this.member = member;
    }
}
