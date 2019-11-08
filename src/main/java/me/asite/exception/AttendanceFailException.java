package me.asite.exception;

public class AttendanceFailException extends RuntimeException {
    public AttendanceFailException() {
        super();
    }

    public AttendanceFailException(String message) {
        super(message);
    }

    public AttendanceFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttendanceFailException(Throwable cause) {
        super(cause);
    }

    protected AttendanceFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
