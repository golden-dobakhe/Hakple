package com.golden_dobakhe.HakPle.domain.user.myInfo.validator;


import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoErrorCode;
import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoException;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;

public class PhoneNumValidator {
    private static final String PHONE_REGEX = "^\\d{10,11}$";

    public static void validatePhoneNum(String newPhoneNum, String currentPhoneNum, UserRepository userRepository) {
        if (newPhoneNum == null || newPhoneNum.isBlank()) {
            throw new MyInfoException(MyInfoErrorCode.REQUIRED);
        }

        if (newPhoneNum.equals(currentPhoneNum)) {
            throw new MyInfoException(MyInfoErrorCode.SAME_AS_CURRENT);
        }

        if (!newPhoneNum.matches(PHONE_REGEX)) {
            throw new MyInfoException(MyInfoErrorCode.PHONENUM_INVALID);
        }

        if (userRepository.existsByPhoneNum(newPhoneNum)) {
            throw new MyInfoException(MyInfoErrorCode.PHONENUM_DUPLICATE);
        }
    }
}