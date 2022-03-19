package ru.clevertec.ecxeption;

public class ProductProducerNotFoundException extends Exception{
    public ProductProducerNotFoundException() {
    }

    public ProductProducerNotFoundException(String message) {
        super(message);
    }

    public ProductProducerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductProducerNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductProducerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
