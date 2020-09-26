package com.kiseok.sessionskeleton.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BeanConfigTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("PasswordEncoder 테스트")
    @Test
    void test_passwordEncoder() {
        String password = "testPassword";
        String encodedPassword = passwordEncoder.encode(password);

        assertTrue(passwordEncoder.matches(password, encodedPassword));
    }
}
