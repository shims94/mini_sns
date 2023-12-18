package com.mysideproj.sns.service;

import com.mysideproj.sns.controller.repository.UserEntityRepository;
import com.mysideproj.sns.exception.SnsApplicationException;
import com.mysideproj.sns.fixture.UserEntityFixture;
import com.mysideproj.sns.model.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    
    // 회원가입 정상동작 테스트
    @Test
    void join_succeed() {
        String userName = "userName";
        String password = "password";

        // mocking 회원가입이 된적이 없기때문에 Optional.empty 반환해야함(userName 으로 DB 에서 찾으면 당연히 없어야함)
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        // save(저장된 entity 반환)시 userEntity 타입의 목킹을 Optional 로 리턴
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName,password)));

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    // 회원가입시 userName 으로 회원가입한 user 가 이미 존재
    @Test
    void join_error_userExist() {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        //
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));
        // SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () 이런식으로 핸들링가능
    }

    // 로그인 정상동작 테스트
    @Test
    void login_succeed() {
        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        // 정상 동작시에는 DoesNotThrow, 에러가 나면 안되기때문
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    // 로그인 userName 으로 회원가입한 user 가 이미 존재
    @Test
    void login_error_userExist() {
        String userName = "userName";
        String password = "password";


        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
        // SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () 이런식으로 핸들링가능
    }

    // 로그인 시 패스워드 틀림
    @Test
    void login_error_incorrectPassword() {
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(userName,password);

        // mocking
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, wrongPassword));
        // SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () 이런식으로 핸들링가능
    }
}