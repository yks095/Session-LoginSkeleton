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

    private static final String SIGN_UP = "/sign-up";
    private static final String SIGN_UP_FORM = "/sign-up-form";
    private static final String SIGN_IN = "/sign-in";

    @DisplayName("/sign-up 화면 조회 -> 200 OK")
    @Test
    public void load_sign_up() throws Exception {
        mockMvc.perform(get(SIGN_UP))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
        ;
    }

    @DisplayName("/sign up 유저 등록 성공 -> 201 CREATED")
    @Test
    public void save_sign_up() throws Exception {
        AccountDto accountDTO = createAccountDto();

        mockMvc.perform(post(SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @DisplayName("/sign-up-form 화면 조회 -> 200 OK")
    @Test
    public void load_sign_up_form() throws Exception {
        mockMvc.perform(get(SIGN_UP_FORM))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up-form"))
        ;
    }

    @DisplayName("/sign-up-form 유저 등록 성공 -> 201 CREATED")
    @Test
    public void successSignUpForm() throws Exception {
        AccountDto accountDto = createAccountDto();

        mockMvc.perform(post(SIGN_UP_FORM)
                .param("email", accountDto.getEmail())
                .param("password", accountDto.getPassword())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in"))
                .andExpect(view().name("redirect:/sign-in"))
        ;
    }

    @DisplayName("로그인 화면 조회 -> 200 OK")
    @Test
    public void load_sign_in() throws Exception {
        mockMvc.perform(get(SIGN_IN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-in"))
        ;
    }

    @DisplayName("로그인 성공 with /sign-up -> 302 REDIRECT")
    @Test
    void success_login_with_signUp() throws Exception   {
        AccountDto accountDTO = createAccountDto();

        mockMvc.perform(post(SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
        ;

        mockMvc.perform(formLogin()
                .loginProcessingUrl(SIGN_IN)
                .user(accountDTO.getEmail())
                .password(accountDTO.getPassword()))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());

    }

    @DisplayName("로그인 성공 with /sign-up-form -> 302 REDIRECT")
    @Test
    void success_login_with_signUpForm() throws Exception   {
        AccountDto accountDto = createAccountDto();

        mockMvc.perform(post(SIGN_UP_FORM)
                .param("email", accountDto.getEmail())
                .param("password", accountDto.getPassword())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sign-in"))
                .andExpect(view().name("redirect:/sign-in"))
        ;

        mockMvc.perform(formLogin()
                .loginProcessingUrl(SIGN_IN)
                .user(accountDto.getEmail())
                .password(accountDto.getPassword()))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());

    }

    private AccountDto createAccountDto() {
        String email = "test@email.com";
        String password = "password";

        return AccountDto.builder()
                .email(email)
                .password(password)
                .build();
    }
}

