package com.kiseok.sessionskeleton.account;

import com.kiseok.sessionskeleton.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AccountController {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String getMain(Model model, @AuthAccount Account currentUser) {
        model.addAttribute("name", currentUser.getEmail());

        return "index";
    }

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody @Valid AccountDto accountDto, Errors errors) {
        if(errors.hasErrors())  {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accountRepository.save(accountDto.toEntity(passwordEncoder));
       return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @GetMapping("/sign-up-form")
    public String getSignUpForm(Model model)   {
        model.addAttribute("accountDto", new AccountDto());
        return "account/sign-up-form";
    }

    @PostMapping("/sign-up-form")
    public String signUpForm(@Valid AccountDto accountDto, Errors errors)   {
        if(errors.hasErrors())  {
            return "redirect:/sign-up-form";
        }
        accountRepository.save(accountDto.toEntity(passwordEncoder));

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "account/sign-in";
    }

}

