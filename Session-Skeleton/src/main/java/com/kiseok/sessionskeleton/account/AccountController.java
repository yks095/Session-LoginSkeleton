package com.kiseok.sessionskeleton.account;

import com.kiseok.sessionskeleton.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AccountController {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String getMain(Model model, @AuthenticationPrincipal Account account) {
        model.addAttribute("name", account.getEmail());
        WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        printLogs(account, details);

        return "index";
    }

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody @Valid AccountDto accountDto) {
        accountRepository.save(accountDto.toEntity(passwordEncoder));
       return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @GetMapping("/sign-up-form")
    public String getSignUpForm(Model model)   {
        model.addAttribute("accountDto", new AccountDto());
        return "account/sign-up-form";
    }

    @PostMapping("/sign-up-form")
    public String signUpForm(@Valid AccountDto accountDto)   {
        accountRepository.save(accountDto.toEntity(passwordEncoder));

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "account/sign-in";
    }

    private void printLogs(Account account, WebAuthenticationDetails details) {
        log.info("Email : " + account.getUsername());
        log.info("Password : " + passwordEncoder.matches("1234", account.getPassword()));
        log.info("ROLE : " + account.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        log.info("SessionID : " + details.getSessionId());
        log.info("RemoteAddress : " + details.getRemoteAddress());
    }
}

