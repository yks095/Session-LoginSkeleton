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

    // JPA로 데이터를 저장할 때 Enum 값을 어떤 형태로 저장할 지 결정해주는 애노테이션
    // Enum은 JAVA의 열거형 데이터 타입
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
