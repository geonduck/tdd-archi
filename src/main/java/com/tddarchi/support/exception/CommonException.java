package com.tddarchi.support.exception;

import lombok.Getter;

@Getter
public class CommonException extends IllegalStateException {

    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
