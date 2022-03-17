package ru.clevertec.service.interfaces;

import ru.clevertec.entity.DiscountCard;

import java.util.Optional;

public interface DiscountCardService {

    Optional<DiscountCard> findById(String idDiscountCard);
}
