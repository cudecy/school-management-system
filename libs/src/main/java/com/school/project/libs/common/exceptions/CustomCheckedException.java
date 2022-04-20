package com.school.project.libs.common.exceptions;

public class CustomCheckedException extends Exception {
    public CustomCheckedException(String message) {
        super(message);
    }

    public CustomCheckedException(Throwable cause){
        super(cause);
    }

    public CustomCheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}
