package es.eshop.app.exception;

public class ForbbidenException extends RuntimeException{

    public ForbbidenException(String message) {
        super(message);
    }

    public ForbbidenException(Throwable cause) {
        super(cause);
    }

    public ForbbidenException(String message, Throwable cause) {
        super(message, cause);
    }
}
