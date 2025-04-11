package com.golden_dobakhe.HakPle.domain.user.myInfo.validator;

import com.golden_dobakhe.HakPle.domain.user.myInfo.repository.MyInfoRepository;

public class PhoneNumValidator {
    private static final String PHONE_REGEX = "^\\d{10,11}$";

    public static void validatePhoneNum(String newPhoneNum, String currentPhoneNum, MyInfoRepository myInfoRepository) {
        if (newPhoneNum == null || newPhoneNum.isBlank()) {
            throw new IllegalArgumentException("전화번호는 필수 항목입니다.");
        }

        if (newPhoneNum.equals(currentPhoneNum)) {
            throw new IllegalArgumentException("기존 전화번호와 동일합니다.");
        }

        if (!newPhoneNum.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("전화번호는 숫자만 포함하며, 10~11자리여야 합니다.");
        }

        if (myInfoRepository.existsByPhoneNum(newPhoneNum)) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }
    }
}