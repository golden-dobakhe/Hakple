package com.golden_dobakhe.HakPle.domain.resource.image.service;

import com.golden_dobakhe.HakPle.domain.resource.image.dto.ProfileImageRequestDto;
import com.golden_dobakhe.HakPle.domain.resource.image.entity.Image;
import com.golden_dobakhe.HakPle.domain.resource.image.exception.ImageErrorCode;
import com.golden_dobakhe.HakPle.domain.resource.image.exception.ProfileImageException;
import com.golden_dobakhe.HakPle.domain.resource.image.repository.ImageRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private static final String UPLOAD_DIR = "uploads/profile/";

    // 프로필 사진 등록, 수정
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
        }

        // 파일 저장
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destinationFile = new File(uploadFolder, fileName);
        file.transferTo(destinationFile);

        // 이미지 DB 저장
        String relativePath = "/" + UPLOAD_DIR + fileName;

        Image existingImage = imageRepository.findByUser(user);
        // 수정
        if (existingImage != null) {
            String oldFilePath = System.getProperty("user.dir") + existingImage.getFilePath();
            File oldFile = new File(oldFilePath);
            if (oldFile.exists()) {
                oldFile.delete();
            }

            existingImage.setFilePath(relativePath);
            imageRepository.save(existingImage);
        }
        // 등록
        else {
            Image newImage = Image.builder()
                    .user(user)
                    .filePath(relativePath)
                    .build();
            imageRepository.save(newImage);
            user.setProfileImage(newImage);
            userRepository.save(user);
        }

        return relativePath;
    }

    // 프로필 사진 삭제
    public void deleteProfileImage(String userName) {
        // 유저 조회
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ProfileImageException(ImageErrorCode.USER_NOT_FOUND));

        Image image = imageRepository.findByUser(user);
        if (image == null) {
            throw new ProfileImageException(ImageErrorCode.IMAGE_NOT_FOUND);
        }

        // 파일 삭제
        String deletePath = System.getProperty("user.dir") + image.getFilePath();
        File file = new File(deletePath);
        if (file.exists()) {
            file.delete();
        }

        user.setProfileImage(null);
        userRepository.save(user);

        imageRepository.delete(image);
    }
}