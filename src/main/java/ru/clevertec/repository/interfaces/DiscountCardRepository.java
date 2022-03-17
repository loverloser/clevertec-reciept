package ru.clevertec.repository.interfaces;

import ru.clevertec.entity.DiscountCard;

import java.util.Optional;

public interface DiscountCardRepository {

    Optional<DiscountCard> findById(Long idDiscountCard);
}
