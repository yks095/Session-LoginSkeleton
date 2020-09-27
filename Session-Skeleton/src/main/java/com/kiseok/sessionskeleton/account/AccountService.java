package com.kiseok.sessionskeleton.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loadUserByUsername 진입");
        log.info("username : " + email);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        log.info("DB에서 찾은 user의 password : " + account.getPassword());

        return account;
    }
}
