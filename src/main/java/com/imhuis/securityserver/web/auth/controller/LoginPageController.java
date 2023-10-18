package com.imhuis.securityserver.web.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: imhuis
 * @date: 2022/6/30
 * @description:
 */
@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String login() {
        return "security/login";
    }
}
