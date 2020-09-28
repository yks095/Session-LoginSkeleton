package com.kiseok.sessionskeleton.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiseok.sessionskeleton.account.dto.AccountDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown()    {
        this.accountRepository.deleteAll();
    }

    @DisplayName("회원가입 /sign-up 화면 조회 테스트")
    @Test
    public void getSignUp() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
        ;
    }

    @DisplayName("회원가입 /sign-up 성공 테스트")
    @Test
    public void successSignUp() throws Exception {
        String email = "test@email.com";
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

    @DisplayName("회원가입 /sign-up-form 화면 조회 테스트")
    @Test
    public void getSignUpForm() throws Exception {
        mockMvc.perform(get("/sign-up-form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up-form"))
        ;
    }

    @DisplayName("회원가입 /sign-up-form 성공 테스트")
    @Test
    public void successSignUpForm() throws Exception {
        String email = "test@email.com";
        String password = "password";

        mockMvc.perform(post("/sign-up-form")
                .param("email", email)
                .param("password", password)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in"))
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

    @DisplayName("로그인 성공 테스트 with /sign-up")
    @Test
    void success_login_with_signUp() throws Exception   {
        AccountDto accountDTO = AccountDto.builder()
                .email("test@email.com")
                .password("password")
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
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());

    }

    @DisplayName("로그인 성공 테스트 with /sign-up-form")
    @Test
    void success_login_with_signUpForm() throws Exception   {
        String email = "test@email.com";
        String password = "password";

        mockMvc.perform(post("/sign-up-form")
                .param("email", email)
                .param("password", password)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in"))
        ;

        mockMvc.perform(formLogin()
                .loginProcessingUrl("/sign-in")
                .user(email)
                .password(password))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());

    }

}

