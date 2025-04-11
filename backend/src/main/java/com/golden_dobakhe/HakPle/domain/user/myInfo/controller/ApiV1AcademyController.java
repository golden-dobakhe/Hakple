package com.golden_dobakhe.HakPle.domain.user.myInfo.controller;

import com.golden_dobakhe.HakPle.domain.user.myInfo.service.AcademyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/academies")
public class ApiV1AcademyController {
    private final AcademyService academyService;

    @GetMapping("/register")
    public ResponseEntity<String> registerAcademy(
            @RequestParam("userName") String userName,
            @RequestParam("academyCode") String academyCode
    ) {
        String academyName = academyService.registerAcademy(userName, academyCode);
        return ResponseEntity.ok("학원이 등록되었습니다: " + academyName);
    }
}
