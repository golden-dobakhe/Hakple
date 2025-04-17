package com.golden_dobakhe.HakPle.domain.resource.image.controller;


import com.golden_dobakhe.HakPle.domain.resource.image.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@Tag(name = "Images", description = "이미지 관리 API")
public class ApiV1ImageController {

    //private final AmazonS3 amazonS3; // ✅ 필드 추가
    private final FileService fileService;

    @PostMapping("/upload_local")
    public ResponseEntity<?> uploadFile1(@RequestParam("file") MultipartFile file) {
        try {
            // 파일이 비어있는지 확인
            if (file.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "파일이 비어있습니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 파일 형식 확인 (이미지만 허용)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "이미지 파일만 업로드 가능합니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 로컬에 파일 저장
            String filePath = fileService.saveFile(file);
            
            // 로그 출력
            System.out.println("파일 저장 경로: " + filePath);
            
            // 성공 응답 반환
            return ResponseEntity.ok(filePath);
        } catch (Exception e) {
            // 오류 로그 출력
            System.err.println("파일 업로드 오류: " + e.getMessage());
            e.printStackTrace();
            
            // 오류 응답 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 서버 상태 확인용 엔드포인트 추가
    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("OK");
    }

//    @PostMapping("/upload_S3")
//    public String uploadFile2(@RequestParam("file") MultipartFile file) {
//        String bucketName = "your-bucket";
//        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        metadata.setContentType(file.getContentType());
//
//        try (InputStream inputStream = file.getInputStream()) {
//            amazonS3.putObject(
//                    new PutObjectRequest(bucketName, fileName, inputStream, metadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead)
//            );
//            return amazonS3.getUrl(bucketName, fileName).toString();
//        } catch (IOException e) {
//            throw new RuntimeException("파일 업로드 실패", e);
//        }
//    }
}
