package com.golden_dobakhe.HakPle.domain.user.admin.controller;

import com.golden_dobakhe.HakPle.domain.user.admin.dto.AcademyRequestDto;
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

    @PostMapping("/academies/register")
    @Operation(summary = "학원 등록", description = "새로운 학원을 등록하고 학원 코드를 반환합니다.")
    public ResponseEntity<String> createAcademy(@RequestBody @Valid AcademyRequestDto requestDto) {
        String academyCode = adminService.createAcademy(requestDto);
        return ResponseEntity.ok(academyCode);
    }

    @PostMapping("/boards/{id}/pending")
    @Operation(summary = "게시글 상태를 PENDING으로 변경", description = "신고된 게시글을 PENDING 상태로 변경합니다.")
    public ResponseEntity<Void> setBoardPending(@PathVariable(name = "id") Long id) {
        adminService.setBoardPending(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comments/{id}/pending")
    @Operation(summary = "댓글 상태를 PENDING으로 변경", description = "신고된 댓글을 PENDING 상태로 변경합니다.")
    public ResponseEntity<Void> setCommentPending(@PathVariable(name = "id")  Long id) {
        adminService.setCommentPending(id);
        return ResponseEntity.ok().build();
    }
}


