package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.service.DiscountCardService;

import java.util.Optional;

@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepositoryImpl discountCardRepository;

    @Override
    public Optional<DiscountCard> findById(Long idDiscountCard) {
        return discountCardRepository.findById(idDiscountCard);
    }
}
