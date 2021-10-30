package org.chat.dto.exceptions;

public class DtoException extends Exception {

    private final DtoErrorCode errorCode;

    public DtoException(DtoErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DtoErrorCode getErrorCode() {
        return errorCode;
    }

}
