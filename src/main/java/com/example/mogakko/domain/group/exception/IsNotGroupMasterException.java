package com.example.mogakko.domain.group.exception;

public class IsNotGroupMasterException extends RuntimeException {
    public IsNotGroupMasterException() {
        super();
    }

    public IsNotGroupMasterException(String message) {
        super(message);
    }

    public IsNotGroupMasterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsNotGroupMasterException(Throwable cause) {
        super(cause);
    }

    protected IsNotGroupMasterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
