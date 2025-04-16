package com.golden_dobakhe.HakPle.domain.user.user.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Value;


import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${nurigo.api-key}")
    private String apiKey;

    @Value("${nurigo.secret-key}")
    private String secretKey;

    @Value("${nurigo.sender-phone}")
    private String senderPhone;

    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, "https://api.coolsms.co.kr");
    }
    public void sendVerificationCode(String phoneNumber) {
        String code = generateAuthCode();

        // Redis에 3분 저장
        redisTemplate.opsForValue().set("sms:" + phoneNumber, code, Duration.ofMinutes(3));

        Message message = new Message();
        message.setFrom(senderPhone);
        message.setTo(phoneNumber);
        message.setText("[HakPle] 인증번호 [" + code + "] 를 입력해주세요.");

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            throw new RuntimeException("문자 전송 실패: 메시지를 받지 못함", e);
        } catch (NurigoEmptyResponseException e) {
            throw new RuntimeException("문자 전송 실패: 응답 없음", e);
        } catch (NurigoUnknownException e) {
            throw new  RuntimeException("문자 전송 실패: 알 수 없는 오류", e);
        }
    }

    public boolean verifyCode(String phone, String code) {
        String savedCode = redisTemplate.opsForValue().get("sms:" + phone);
        return code.equals(savedCode);
    }

    private String generateAuthCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}
