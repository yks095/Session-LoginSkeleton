package com.kiseok.sessionskeleton.config.auth;

import com.kiseok.sessionskeleton.account.Account;
import lombok.Getter;
import java.io.Serializable;

@Getter
public class SessionAccount implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionAccount(Account account) {
        this.name = account.getName();
        this.email = account.getEmail();
        this.picture = account.getPicture();
    }
}
