package ru.clevertec.repository.interfaces;

import ru.clevertec.entity.DiscountCard;

import java.util.List;
import java.util.Optional;

public interface DiscountCardRepository {

    List<DiscountCard> getAll();

    Optional<DiscountCard> findById(Long idDiscountCard);

    DiscountCard addDiscountCard(DiscountCard discountCard);

    boolean updateDiscountCard(Long idDiscountCard, DiscountCard discountCard);

    boolean removeDiscountCard(Long idDiscountCard);
}
