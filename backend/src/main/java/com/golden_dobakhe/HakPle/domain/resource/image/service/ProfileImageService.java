package com.golden_dobakhe.HakPle.domain.resource.image.service;

import com.golden_dobakhe.HakPle.domain.resource.image.dto.ProfileImageRequestDto;
import com.golden_dobakhe.HakPle.domain.resource.image.entity.Image;
import com.golden_dobakhe.HakPle.domain.resource.image.exception.ImageErrorCode;
import com.golden_dobakhe.HakPle.domain.resource.image.exception.ProfileImageException;
import com.golden_dobakhe.HakPle.domain.resource.image.repository.ImageRepository;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private static final String UPLOAD_DIR = "uploads/profile/";

    // 프로필 사진 등록
    public String uploadProfileImage(ProfileImageRequestDto dto, MultipartFile file) throws IOException {
        // 유저 조회
        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new ProfileImageException(ImageErrorCode.USER_NOT_FOUND));

        // 파일 유효성 검사
        if (file == null || file.isEmpty()) {
            throw new ProfileImageException(ImageErrorCode.FILE_EMPTY);
        }

        // 디렉토리 확인 및 생성
        String uploadPath = System.getProperty("user.dir") + File.separator + UPLOAD_DIR;
        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
            System.out.println("[디버그] 디렉토리 생성됨: " + uploadFolder.mkdirs());
        }

        // 파일 저장
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destinationFile = new File(uploadFolder, fileName);
        file.transferTo(destinationFile);
        System.out.println("[디버그] 저장할 경로: " + destinationFile.getAbsolutePath());

        // 이미지 DB 저장
        String relativePath = "/" + UPLOAD_DIR + fileName;
        Image image = imageRepository.findByUser(user);

        if (image != null) {
            image.setFilePath(relativePath);
        } else {
            image = Image.builder()
                    .user(user)
                    .filePath(relativePath)
                    .build();
        }
        imageRepository.save(image);

        user.setProfileImage(image);
        userRepository.save(user);

        System.out.println("[디버그] 최종 저장 경로: " + relativePath);
        return relativePath;
    }
}