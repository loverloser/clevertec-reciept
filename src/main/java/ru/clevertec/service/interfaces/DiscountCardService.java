package ru.clevertec.service.interfaces;

import ru.clevertec.entity.DiscountCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountCardService {

    List<DiscountCard> getAll();

    Optional<DiscountCard> findById(String idDiscountCard);

    boolean updateDiscountCard(Map<String, String> params);

    boolean removeDiscountCard(String idDiscountCard);

    DiscountCard addDiscountCard(Map<String, String> params);
}
