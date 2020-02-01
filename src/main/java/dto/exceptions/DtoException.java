package dto.exceptions;

public class DtoException extends Exception {

    private DtoErrorCode errorCode;

    public DtoException(DtoErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DtoErrorCode getErrorCode() {
        return errorCode;
    }

}
