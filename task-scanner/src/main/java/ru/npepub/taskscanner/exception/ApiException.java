package ru.npepub.taskscanner.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int status;
    private final String message;

    public ApiException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiException(Throwable cause, int status, String message) {
        super(cause);
        this.status = status;
        this.message = message;
    }
}
