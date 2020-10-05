package com.kiseok.sessionskeleton.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder @Getter @Entity
@AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String picture;

    @Enumerated(value = EnumType.STRING)
    private AccountRole accountRole;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    public Account update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

}
