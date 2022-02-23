package ru.clevertec.service;

import ru.clevertec.entity.DiscountCard;

import java.util.Optional;

public interface DiscountCardService {
    Optional<DiscountCard> findById(Long idDiscountCard);
}
