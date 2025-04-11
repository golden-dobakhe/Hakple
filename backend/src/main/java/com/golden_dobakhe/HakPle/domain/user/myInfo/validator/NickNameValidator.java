package com.golden_dobakhe.HakPle.domain.user.myInfo.validator;

import com.golden_dobakhe.HakPle.domain.user.myInfo.repository.MyInfoRepository;

public class NickNameValidator {
    private static final String NICKNAME_REGEX = "^[a-zA-Z가-힣0-9_.-]{2,20}$";

    public static void validateNickName(String newNickName, String currentNickName, MyInfoRepository myInfoRepository) {
        if (newNickName == null || newNickName.isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수입니다.");
        }

        if (newNickName.equals(currentNickName)) {
            throw new IllegalArgumentException("기존 닉네임과 동일합니다.");
        }

        if (!newNickName.matches(NICKNAME_REGEX)) {
            throw new IllegalArgumentException("닉네임은 한글/영문 2~20자, 공백 없이 특수 기호는 _, -, . 만 사용 가능합니다.");
        }

        if (myInfoRepository.existsByNickName(newNickName)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
    }
}
