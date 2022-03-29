package ru.clevertec.ecxeption;

public class AddProductProducerException extends Exception{
    public AddProductProducerException() {
    }

    public AddProductProducerException(String message) {
        super(message);
    }

    public AddProductProducerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddProductProducerException(Throwable cause) {
        super(cause);
    }

    public AddProductProducerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
