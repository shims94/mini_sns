package com.mysideproj.sns.fixture;

import com.mysideproj.sns.model.entity.UserEntity;


// 테스트용 UserEntity
public class UserEntityFixture {

    public static UserEntity get(String userName, String password) {
        UserEntity result = new UserEntity();
        result.setId(1);
        result.setUserName(userName);
        result.setPassword(password);

        return result;
    }
}
