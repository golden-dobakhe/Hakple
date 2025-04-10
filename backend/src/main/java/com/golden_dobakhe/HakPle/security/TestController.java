package com.golden_dobakhe.HakPle.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String home() {
        return "야스";
    }

    @GetMapping("/success")
    public String success() {
        return "성공";
    }

    @GetMapping("failure")
    public String failure() {
        return "실패";
    }
}
