package com.ttasjwi.ssia.adapter.output.persistence.repository;

import com.ttasjwi.ssia.adapter.output.persistence.data.MemberData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberData, Long> {

    @Query("SELECT m " +
            "FROM MemberData as m " +
            "JOIN FETCH m.authorities as at " +
            "WHERE m.name = :name")
    Optional<MemberData> findByNameWithAuthorities(String name);
}
