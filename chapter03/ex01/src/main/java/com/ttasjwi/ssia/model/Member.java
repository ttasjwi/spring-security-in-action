package com.ttasjwi.ssia.model;

public class Member {

    private Long id;
    private String name;
    private String password;
    private String authority;

    public Member(Long id, String name, String password, String authority) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthority() {
        return authority;
    }
}
