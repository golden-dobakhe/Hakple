package com.golden_dobakhe.HakPle.domain.user.myInfo.service;

import com.golden_dobakhe.HakPle.domain.user.entity.Academy;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.myInfo.util.AcademyCodeParser;
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
        // 1. 유효성 검사
        AcademyCodeValidator.validateAcademyId(academyCode);

        // 2. 전화번호 뒷자리 추출
        String phoneSuffix = AcademyCodeParser.extractPhoneSuffix(academyCode);

        // 3. 학원 찾기
        Optional<Academy> academyOpt = academyRepository.findByPhoneNumEndsWith(phoneSuffix);
        Academy academy = academyOpt.orElseThrow(() -> new IllegalArgumentException("해당 학원 코드를 가진 학원을 찾을 수 없습니다."));

        // 4. 사용자 찾기
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 5. 학원코드 저장
        user.setAcademyId(academyCode);

        return academy.getAcademyName();
    }
}