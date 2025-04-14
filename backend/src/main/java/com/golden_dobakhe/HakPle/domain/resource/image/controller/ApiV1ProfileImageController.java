package com.golden_dobakhe.HakPle.domain.resource.image.controller;

import com.golden_dobakhe.HakPle.domain.resource.image.dto.ProfileImageRequestDto;
import com.golden_dobakhe.HakPle.domain.resource.image.service.ProfileImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile-images")
@Tag(name = "Profile Image Controller", description = "사용자 프로필 이미지 관리 API")
public class ApiV1ProfileImageController {
    private final ProfileImageService profileImageService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/profile";

    @Operation(summary = "프로필 이미지 등록", description = "사용자가 자신의 프로필 사진을 업로드합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "업로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("userName") String userName,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        ProfileImageRequestDto dto = new ProfileImageRequestDto(userName);
        String path = profileImageService.uploadProfileImage(dto, file);
        return ResponseEntity.ok("프로필 이미지 업로드 성공: " + path);
    }
}