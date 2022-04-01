package ru.clevertec.service.interfaces;

import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.DiscountCard;

import java.util.List;
import java.util.Optional;

public interface DiscountCardService {

    List<DiscountCard> getAll();

    Optional<DiscountCard> findById(Long idDiscountCard);

    Optional<DiscountCard> addDiscountCard(DiscountCard discountCard);

    boolean updateDiscountCard(Long id, DiscountCard discountCard) throws ServiceException;

    boolean removeDiscountCard(Long idDiscountCard) throws ServiceException;
}
