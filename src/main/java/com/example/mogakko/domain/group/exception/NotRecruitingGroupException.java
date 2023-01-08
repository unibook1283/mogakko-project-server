package com.example.mogakko.domain.group.exception;

public class NotRecruitingGroupException extends RuntimeException {
    public NotRecruitingGroupException() {
        super();
    }

    public NotRecruitingGroupException(String message) {
        super(message);
    }

    public NotRecruitingGroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotRecruitingGroupException(Throwable cause) {
        super(cause);
    }

    protected NotRecruitingGroupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
