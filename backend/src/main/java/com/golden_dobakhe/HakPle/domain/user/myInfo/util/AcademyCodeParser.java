package com.golden_dobakhe.HakPle.domain.user.myInfo.util;

public class AcademyCodeParser {

    // 호출 전에 Validator.AcademyCodeValidator 를 반드시 실행했다고 가정
    public static String extractPrefix(String academyCode) {
        return academyCode.substring(0, 3);
    }

    public static String extractPhoneSuffix(String academyCode) {
        return academyCode.substring(3, 7);
    }

    public static String extractRandomTail(String academyCode) {
        return academyCode.substring(7, 10);
    }
}
