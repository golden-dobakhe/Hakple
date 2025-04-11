package com.golden_dobakhe.HakPle.domain.user.controller;

import com.golden_dobakhe.HakPle.domain.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ApiV1UserController {
    private final UserService userService;


}
