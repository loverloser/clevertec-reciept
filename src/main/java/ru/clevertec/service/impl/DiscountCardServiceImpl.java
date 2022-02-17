package ru.clevertec.service.impl;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.service.DiscountCardService;

public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepositoryImpl discountCardRepository = DiscountCardRepositoryImpl.getInstance();

    @Override
    public DiscountCard findById(Long idDiscountCard) {
        return discountCardRepository.findById(idDiscountCard);
    }
}
