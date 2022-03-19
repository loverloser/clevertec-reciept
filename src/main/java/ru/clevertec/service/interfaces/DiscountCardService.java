package ru.clevertec.service.interfaces;

import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.DiscountCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountCardService {

    List<DiscountCard> getAll();

    Optional<DiscountCard> findById(String idDiscountCard);

    boolean updateDiscountCard(Map<String, String> params) throws ServiceException;

    boolean removeDiscountCard(String idDiscountCard) throws ServiceException;

    DiscountCard addDiscountCard(Map<String, String> params) throws ServiceException;
}
