package com.mysideproj.sns.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

// 에러 전달이 명확하고 쉽게 RuntimesException 커스텀
// TODO : Develop
@Getter
@AllArgsConstructor
public class SnsApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode, getMessage(), message);
    }
}
