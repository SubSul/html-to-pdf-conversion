package au.com.utility.api.core.exception;

public class ApplicationException extends RuntimeException {
    private int code;
    private String message;

    public ApplicationException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
