package com.example.mogakko.domain.group.exception;

public class AlreadyAppliedException extends RuntimeException {
    public AlreadyAppliedException() {
        super();
    }

    public AlreadyAppliedException(String message) {
        super(message);
    }

    public AlreadyAppliedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyAppliedException(Throwable cause) {
        super(cause);
    }

    protected AlreadyAppliedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
