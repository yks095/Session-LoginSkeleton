package com.kiseok.sessionskeleton.account;

import com.kiseok.sessionskeleton.config.auth.OAuthAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AccountAdapter implements UserDetails, OAuth2User {

    private Account account;
    private OAuthAttributes attributes;

    public AccountAdapter(Account account) {
        this.account = account;
    }

    public AccountAdapter(Account account, OAuthAttributes attributes) {
        this.account = account;
        this.attributes = attributes;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String getName() {
        return account.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
