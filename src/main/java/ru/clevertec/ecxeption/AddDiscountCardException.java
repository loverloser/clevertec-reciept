package ru.clevertec.ecxeption;

public class AddDiscountCardException extends Exception{

    public AddDiscountCardException() {
    }

    public AddDiscountCardException(String message) {
        super(message);
    }

    public AddDiscountCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddDiscountCardException(Throwable cause) {
        super(cause);
    }

    public AddDiscountCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
