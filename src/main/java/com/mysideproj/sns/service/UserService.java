package com.mysideproj.sns.service;

import com.mysideproj.sns.exception.ErrorCode;
import com.mysideproj.sns.repository.UserEntityRepository;
import com.mysideproj.sns.exception.SnsApplicationException;
import com.mysideproj.sns.model.User;
import com.mysideproj.sns.model.entity.UserEntity;
import com.mysideproj.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder; // 비밀번호 암호화

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-times-ms}")
    private Long expiredTimesMs;

    public User loadUserByUserName(String userName) {
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded",userName)));
    }


    @Transactional // exception 발생시 save부분이 롤백이되면서 저장되지 않게.
    public User join(String userName, String password) {
        // join 을 위한 userName 으로 회원가입된 user 있는지 체크
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        // join : user 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    // login 인증 방법은 JWT 사용 return String
    // TODO : implement
    public String login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded", userName)));

        // 비밀번호 체크 (입력 == 등록)
        if(!encoder.matches(password, userEntity.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        
        // 토큰 생성
        String token = JwtTokenUtils.generateToken(userName, secretKey, expiredTimesMs);

        return token;
    }
}
