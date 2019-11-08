package me.asite.exception;

public class CannotFindByIDException extends RuntimeException {
    public CannotFindByIDException() {
        super();
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
