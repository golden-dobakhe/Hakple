package com.golden_dobakhe.HakPle.domain.user.myInfo.service;

import com.golden_dobakhe.HakPle.domain.user.entity.Academy;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.myInfo.validator.AcademyCodeValidator;
import com.golden_dobakhe.HakPle.domain.user.repository.AcademyRepository;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import java.util.Optional;
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
        String phoneSuffix = academyCode.substring(3, 7);

        // 학원 찾기
        Optional<Academy> academyOpt = academyRepository.findByPhoneNumEndsWith(phoneSuffix);
        Academy academy = academyOpt.orElseThrow(() -> new UserException(UserErrorCode.ACADEMYID_NOT_FOUND));

        // 사용자 찾기
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserException(UserErrorCode.USERNAME_NOT_FOUND));

        // 학원코드 저장
        user.setAcademyId(academyCode);

        return academy.getAcademyName();
    }
}