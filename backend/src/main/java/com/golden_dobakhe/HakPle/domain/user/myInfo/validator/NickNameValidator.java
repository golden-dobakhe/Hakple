package com.golden_dobakhe.HakPle.domain.user.myInfo.validator;


import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoErrorCode;
import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoException;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;

public class NickNameValidator {
    private static final String NICKNAME_REGEX = "^[a-zA-Z가-힣0-9_.-]{2,20}$";

    public static void validateNickName(String newNickName, String currentNickName, UserRepository userRepository) {
        if (newNickName == null || newNickName.isBlank()) {
            throw new MyInfoException(MyInfoErrorCode.REQUIRED);
        }

        if (newNickName.equals(currentNickName)) {
            throw new MyInfoException(MyInfoErrorCode.SAME_AS_CURRENT);
        }

        if (!newNickName.matches(NICKNAME_REGEX)) {
            throw new MyInfoException(MyInfoErrorCode.NICKNAME_INVALID);
        }

        if (userRepository.existsByNickName(newNickName)) {
            throw new MyInfoException(MyInfoErrorCode.NICKNAME_DUPLICATE);
        }
    }
}
