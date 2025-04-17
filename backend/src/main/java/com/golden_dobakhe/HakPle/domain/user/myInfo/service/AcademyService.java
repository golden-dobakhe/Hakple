package com.golden_dobakhe.HakPle.domain.user.myInfo.service;

import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.user.entity.Academy;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.myInfo.validator.AcademyCodeValidator;
import java.util.Optional;

import com.golden_dobakhe.HakPle.domain.user.user.repository.AcademyRepository;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AcademyService {
    private final UserRepository userRepository;
    private final AcademyRepository academyRepository;

    @Transactional
    public String registerAcademy(String userName, String academyCode) {
        // 유효성 검사
        AcademyCodeValidator.validateAcademyId(academyCode);

        // 전화번호 뒷자리 추출
        String phoneSuffix = extractPhoneSuffix(academyCode);

        // 학원 찾기
        Optional<Academy> academyOpt = academyRepository.findByPhoneNumEndsWith(phoneSuffix);
        Academy academy = academyOpt.orElseThrow(() -> new UserException(UserErrorCode.ACADEMY_ID_NOT_FOUND));

        // 사용자 찾기
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 학원코드 저장
        user.setAcademyId(academyCode);

        return academy.getAcademyName();
    }

    public String extractPhoneSuffix(String academyCode) {
        return academyCode.substring(3, 7);
    }
}