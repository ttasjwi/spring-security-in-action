package com.ttasjwi.ssia.member.application.ports.input;

import com.ttasjwi.ssia.security.user.SecurityMember;
import com.ttasjwi.ssia.member.application.ports.output.MemberOutputPort;
import com.ttasjwi.ssia.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsInputPort implements UserDetailsService {

    private final MemberOutputPort memberOutputPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberOutputPort.findByNameWithAuthorities(username);
        return new SecurityMember(member);
    }
}
