package com.mysideproj.sns.exception;


import lombok.AllArgsConstructor;

// 에러 전달이 명확하고 쉽게 RuntimesException 커스텀
// TODO : develop
@AllArgsConstructor
public class SnsApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;
}
