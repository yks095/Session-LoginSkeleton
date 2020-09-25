package com.kiseok.sessionskeleton.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiseok.sessionskeleton.account.dto.AccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("리소스 적용 테스트")
    @Test
    public void getResource() throws Exception {
        mockMvc.perform(get("/css/base.css"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("CSRF 필터 Test")
    @Test
    void csrf_filter_test() throws Exception  {
        String email = "email@email.com";
        String password = "password";

        AccountDto accountDTO = AccountDto.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;
    }
}
