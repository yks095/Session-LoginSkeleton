package com.kiseok.sessionskeleton.account.dto;

import com.kiseok.sessionskeleton.account.Account;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Builder
public class AccountDto {

    public String email;

    public String password;

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .build();
    }
}