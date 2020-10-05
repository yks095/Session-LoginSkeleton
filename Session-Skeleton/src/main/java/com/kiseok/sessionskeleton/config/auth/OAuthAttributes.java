package com.kiseok.sessionskeleton.config.auth;

import com.kiseok.sessionskeleton.account.Account;
import com.kiseok.sessionskeleton.account.AccountRole;
import com.kiseok.sessionskeleton.account.SocialType;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private SocialType socialType;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, SocialType socialType) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.socialType = socialType;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes)   {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .socialType(SocialType.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build()
        ;
    }

    public Account toEntity()   {
        return Account.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .accountRole(AccountRole.USER)
                .socialType(socialType)
                .build();
    }


}
