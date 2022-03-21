package ru.clevertec.validator;

import java.util.Map;

import static ru.clevertec.constants.ApplicationConstants.PRODUCT_NAME;
import static ru.clevertec.constants.ApplicationConstants.PRODUCT_PRICE;
import static ru.clevertec.constants.ApplicationConstants.PRODUCT_PRODUCER_ID;

public class ProductValidator {

    private static final String ID_REGEX = "^[0-9]\\d*";
    private static final String NAME_REGEX = "^[A-Za-zА-ЯА-я]\\w+";
    private static final String PRICE_REGEX = "\\d+\\.?\\d?+";

    public static boolean isValid(Map<String, String> params) {
        return params.get(PRODUCT_NAME).matches(NAME_REGEX)
               && params.get(PRODUCT_PRICE).matches(PRICE_REGEX)
               && params.get(PRODUCT_PRODUCER_ID).matches(ID_REGEX);
    }
}
