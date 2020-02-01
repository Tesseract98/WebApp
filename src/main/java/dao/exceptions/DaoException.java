package dao.exceptions;

public class DaoException extends Exception {

    private DaoErrorCode errorCode;

    public DaoException(DaoErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DaoErrorCode getErrorCode() {
        return errorCode;
    }

}
