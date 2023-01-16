package com.example.mogakko.global.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private BadRequestCode codeAndMessage = BadRequestCode.findByClass(this.getClass());

    private String message;
    private String code;

    public BadRequestException() {
        this.message = codeAndMessage.getMessage();
        this.code = codeAndMessage.getCode();
    }
}
