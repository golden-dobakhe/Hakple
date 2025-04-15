package com.golden_dobakhe.HakPle.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//
@Configuration
public class AppConfig {

    @Getter
    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        AppConfig.objectMapper = objectMapper;
    }

    @Getter
    private static String siteFrontUrl;

//    @Value("${custom.site.frontUrl}")
//    public void setSiteFrontUrl(String siteFrontUrl) {
//        AppConfig.siteFrontUrl = siteFrontUrl;
//    }

    public static boolean isNotProd() {
        return true;
    }
}
