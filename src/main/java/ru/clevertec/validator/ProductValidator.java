package ru.clevertec.validator;

import java.util.Map;

import static ru.clevertec.constants.ApplicationConstants.PRODUCT_NAME;
import static ru.clevertec.constants.ApplicationConstants.PRODUCT_PRICE;
import static ru.clevertec.constants.ApplicationConstants.PRODUCT_PRODUCER_ID;

public class ProductValidator {

    private static final String idRegex = "^[0-9]\\d*";
    private static final String nameRegex = "^[A-Za-zА-ЯА-я]\\w+";
    private static final String priceRegex = "\\d+\\.?\\d?+";

    public static boolean isValid(Map<String, String> params) {
        return params.get(PRODUCT_NAME).matches(nameRegex)
               && params.get(PRODUCT_PRICE).matches(priceRegex)
               && params.get(PRODUCT_PRODUCER_ID).matches(idRegex);
    }
}
