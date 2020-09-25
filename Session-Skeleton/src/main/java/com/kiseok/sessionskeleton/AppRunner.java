package com.kiseok.sessionskeleton;

import com.kiseok.sessionskeleton.account.Account;
import com.kiseok.sessionskeleton.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppRunner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) {
        Account account = Account.builder()
                .email("test@email.com")
                .password(passwordEncoder.encode("1234"))
                .build();

        accountRepository.save(account);
    }
}
