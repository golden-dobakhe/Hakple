package com.golden_dobakhe.HakPle.domain.post.comment.like.controller;

import com.golden_dobakhe.HakPle.SecurityTestUtils;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.global.entity.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setupAuthentication() {
        User fakeUser = User.builder()
                .id(111L)
                .userName("testuser")
                .nickName("홍길동")
                .phoneNum("010-1234-5678")
                .password("encoded")
                .status(Status.ACTIVE)
                .build();

        SecurityTestUtils.setAuthentication(fakeUser);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityTestUtils.clearAuthentication();
    }

    @Test
    void 좋아요_리스트_조회() throws Exception {
        mockMvc.perform(get("/api/v1/likes/my/comments"))
                .andExpect(status().isOk());
    }
}
