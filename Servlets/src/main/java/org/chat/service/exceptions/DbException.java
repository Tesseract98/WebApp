package org.chat.service.exceptions;

public class DbException extends Exception {

    private DbErrorCode errorCode;

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(DbErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DbErrorCode getErrorCode() {
        return errorCode;
    }

}
