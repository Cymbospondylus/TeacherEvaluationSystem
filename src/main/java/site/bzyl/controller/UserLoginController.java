package site.bzyl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserLoginController {
    @GetMapping("/login")
    public String login() {
        return "login success";
    }
}
