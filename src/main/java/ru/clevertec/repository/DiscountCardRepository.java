package ru.clevertec.repository;

import ru.clevertec.entity.DiscountCard;

public interface DiscountCardRepository {
    DiscountCard findById(Long idDiscountCard);
}
