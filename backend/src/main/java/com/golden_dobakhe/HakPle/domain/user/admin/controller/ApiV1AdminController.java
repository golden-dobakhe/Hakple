package com.golden_dobakhe.HakPle.domain.user.admin.controller;

import com.golden_dobakhe.HakPle.domain.user.admin.dto.AdminLoginDto;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.AdminRegisterDto;
import com.golden_dobakhe.HakPle.domain.user.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "관리자 관련 API")
public class ApiV1AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 회원가입", description = "관리자 계정을 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AdminRegisterDto dto) {
        String result = adminService.registerAdmin(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "관리자 로그인", description = "관리자 로그인 후 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AdminLoginDto dto) {
        Map<String, String> tokens = adminService.loginAdmin(dto);
        return ResponseEntity.ok(tokens);
    }

    @Operation(summary = "관리자 전용 대시보드", description = "ADMIN 권한이 있어야 접근 가능합니다.")
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("관리자만 접근 가능한 대시보드입니다.");
    }
}


