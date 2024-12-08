package com.example.notemanager.controller;

import com.example.notemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
        // TODO add logic to authenticate the user
        return "redirect:/note/list";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            userService.createUser(username, password);
        } catch (Exception e) {
            log.error("Error creating the user {}", e.getMessage());
        }
        log.info("User {} created", username);
        return "redirect:/signin";
    }
}
