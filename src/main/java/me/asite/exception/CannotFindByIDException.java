package me.asite.exception;

public class CannotFindByIDException extends RuntimeException {
    public CannotFindByIDException() {
        super("아이디에 맞는 데이터가 없습니다.");
    }

    public CannotFindByIDException(String message) {
        super(message);
    }

    public CannotFindByIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindByIDException(Throwable cause) {
        super(cause);
    }

    protected CannotFindByIDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
