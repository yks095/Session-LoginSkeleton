package com.kiseok.sessionskeleton.account.dto;

import com.kiseok.sessionskeleton.account.Account;
import com.kiseok.sessionskeleton.account.AccountRole;
import com.kiseok.sessionskeleton.account.SocialType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class AccountDto {

    @Email(message = "이메일 형식을 지켜주세요")
    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    public String email;

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    public String password;

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .socialType(SocialType.PLAIN)
                .accountRole(AccountRole.USER)
                .build();
    }
}