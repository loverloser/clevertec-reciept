package ru.clevertec.ecxeptions;

public class ProductCountException extends Exception {
    public ProductCountException() {
    }

    public ProductCountException(String message) {
        super(message);
    }

    public ProductCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductCountException(Throwable cause) {
        super(cause);
    }

    public ProductCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
