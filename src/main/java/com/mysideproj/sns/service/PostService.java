package com.mysideproj.sns.service;

import com.mysideproj.sns.exception.ErrorCode;
import com.mysideproj.sns.exception.SnsApplicationException;
import com.mysideproj.sns.model.entity.PostEntity;
import com.mysideproj.sns.model.entity.UserEntity;
import com.mysideproj.sns.repository.PostEntityRepository;
import com.mysideproj.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;


    @Transactional
    public void create(String title, String body, String userName) {

        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        postEntityRepository.save(PostEntity.of(title,body,userEntity));

    }
}
