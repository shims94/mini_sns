package com.mysideproj.sns.model.entity;

import com.mysideproj.sns.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"user\"") // PostgreSQL을 쓸때 기본 user테이블이 존재해서 "를 넣어줌
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class UserEntity {

    // Data base 에서 자동 increase 되는 Integer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL에서 지원하는 시퀀스를 사용하기때문에
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "registered_at") // 유저등록시간 디버깅을 위함
    private Timestamp registeredAt;

    @Column(name = "update_at") // 업데이트된 시간
    private Timestamp updatedAt;

    @Column(name = "deleted_at") // 삭제 된 시간
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static UserEntity of(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);

        return userEntity;
    }
}
