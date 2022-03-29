package ru.clevertec.ecxeption;

public class AddProductException extends Exception{
    public AddProductException() {
    }

    public AddProductException(String message) {
        super(message);
    }

    public AddProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddProductException(Throwable cause) {
        super(cause);
    }

    public AddProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
