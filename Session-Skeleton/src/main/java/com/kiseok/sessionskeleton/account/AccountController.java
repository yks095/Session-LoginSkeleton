package com.kiseok.sessionskeleton.account;

import com.kiseok.sessionskeleton.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String getMain(HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByEmail(user.getUsername()).get();
        WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        HttpSession session = request.getSession();

        System.out.println("Email : " + user.getUsername());
        System.out.println("Password : " + passwordEncoder.matches("1234", account.getPassword()));
        System.out.println("ROLE : " + user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        System.out.println("SessionID : " + details.getSessionId());
        System.out.println("RemoteAddress : " + details.getRemoteAddress());
        System.out.println("HttpSession : " + session.getId());

        return "main";
    }

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody AccountDto accountDto) {
        accountRepository.save(accountDto.toEntity(passwordEncoder));
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "account/sign-in";
    }

}

