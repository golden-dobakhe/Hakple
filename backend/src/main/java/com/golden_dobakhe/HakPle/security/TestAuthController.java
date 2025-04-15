package com.golden_dobakhe.HakPle.security;

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.security.dto.LoginDto;
import com.golden_dobakhe.HakPle.security.dto.LoginResponseDto;
import com.golden_dobakhe.HakPle.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

//일단 시험용으로 만들어 놓긴 했는데
//추후 이걸 가지고 복붙 하거나 지울 수 있음
public class TestAuthController {
    private final TestAuthService userservice;
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("여기는 집");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto req) {
        User user= userservice.findByUserName(req);
        //일단은 유저가 있는지 없는지 확인
        //없으면 나가리
        if (user == null)
            return ResponseEntity.status(404).body("님 없음");
        if (!req.getPassword().equals(user.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비번이 틀린댑쇼");

        //있다면? 토큰 만들고
        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getUserName(), user.getNickName(),user.getPhoneNum(), user.getStatus());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getUserName(), user.getNickName(),user.getPhoneNum(), user.getStatus());

        //토큰을 저장하고
        userservice.addRefreshToken(user, refreshToken);


        //만든걸 보내야지
        LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, refreshToken, user.getId(), user.getUserName());
        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("야스");
    }

    @GetMapping("failure")
    public ResponseEntity<String> failure() {
        return ResponseEntity.notFound().build();
    }
}
