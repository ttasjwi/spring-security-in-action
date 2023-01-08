package com.ttasjwi.ssia.member.adapter.output.repository;

import com.ttasjwi.ssia.member.adapter.output.data.MemberData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberData, Long> {

    @Query("SELECT m " +
            "FROM MemberData as m " +
            "JOIN FETCH m.authorities as at " +
            "WHERE m.name = :name")
    Optional<MemberData> findByNameWithAuthorities(@Param("name") String name);
}
