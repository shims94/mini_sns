package com.mysideproj.sns.repository;

import com.mysideproj.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    // UserEntity 없을 수도 있어서 Optional 사용
    Optional<UserEntity> findByUserName(String userName);
}
