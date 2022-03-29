package ru.clevertec.ecxeption;

public class DiscountCardNotFoundException extends Exception{
    public DiscountCardNotFoundException() {
    }

    public DiscountCardNotFoundException(String message) {
        super(message);
    }

    public DiscountCardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountCardNotFoundException(Throwable cause) {
        super(cause);
    }

    public DiscountCardNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
