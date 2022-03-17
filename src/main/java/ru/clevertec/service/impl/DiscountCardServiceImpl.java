package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.service.interfaces.DiscountCardService;

import java.util.Optional;

@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;

    @Override
    public Optional<DiscountCard> findById(String idDiscountCard) {
        return discountCardRepository.findById(Long.parseLong(idDiscountCard));
    }
}
