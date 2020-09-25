package com.kiseok.sessionskeleton.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiseok.sessionskeleton.account.dto.AccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 화면 조회 테스트")
    @Test
    public void getSignUp() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
        ;
    }

    @DisplayName("회원가입 성공 테스트")
    @Test
    public void successSignUp() throws Exception {
        String email = "email@email.com";
        String password = "password";

        AccountDto accountDTO = AccountDto.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @DisplayName("로그인 화면 조회 테스트")
    @Test
    public void getSignIn() throws Exception {
        mockMvc.perform(get("/sign-in"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-in"))
        ;
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    void success_login() throws Exception   {
        AccountDto accountDTO = AccountDto.builder()
                .email("test@email.com")
                .password("pwd")
                .build();

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
        ;

        mockMvc.perform(formLogin()
                .loginProcessingUrl("/sign-in")
                .user(accountDTO.getEmail())
                .password(accountDTO.getPassword()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
        ;
    }
}

