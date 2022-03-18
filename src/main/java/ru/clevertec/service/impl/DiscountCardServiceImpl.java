package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.service.interfaces.DiscountCardService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;

    @Override
    public List<DiscountCard> getAll() {
        return discountCardRepository.getAll();
    }

    @Override
    public Optional<DiscountCard> findById(String idDiscountCard) {
        return discountCardRepository.findById(Long.parseLong(idDiscountCard));
    }

    @Override
    public boolean updateDiscountCard(Map<String, String> params) {
        DiscountCard discountCard = getProductFromParams(params);
        return discountCardRepository.updateDiscountCard(discountCard.getId(), discountCard);
    }

    @Override
    public boolean removeDiscountCard(String idDiscountCard) {
        return discountCardRepository.removeDiscountCard(Long.parseLong(idDiscountCard));
    }

    @Override
    public DiscountCard addDiscountCard(Map<String, String> params) {
        DiscountCard discountCard = getProductFromParams(params);
        return discountCardRepository.addDiscountCard(discountCard);
    }

    private DiscountCard getProductFromParams(Map<String, String> params){
        DiscountCard discountCard = new DiscountCard();
        if (params.containsKey(ApplicationConstants.DISCOUNT_CARD_ID)){
            Long id = Long.parseLong(params.get(ApplicationConstants.DISCOUNT_CARD_ID));
            discountCard.setId(id);
        }

        double discount = Double.parseDouble(params.get(ApplicationConstants.DISCOUNT_CARD_DISCOUNT));
        discountCard.setDiscount(discount);
        return discountCard;
    }
}
