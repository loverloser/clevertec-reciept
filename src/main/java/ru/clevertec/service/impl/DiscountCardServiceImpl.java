package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.service.interfaces.DiscountCardService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
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
    public boolean updateDiscountCard(Map<String, String> params) throws ServiceException {
        DiscountCard discountCard = getProductFromParams(params);
        try {
            return discountCardRepository.updateDiscountCard(discountCard.getId(), discountCard);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeDiscountCard(String idDiscountCard) throws ServiceException {
        try {
            return discountCardRepository.removeDiscountCard(Long.parseLong(idDiscountCard));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<DiscountCard> addDiscountCard(Map<String, String> params) {
        DiscountCard discountCard = getProductFromParams(params);
        return discountCardRepository.addDiscountCard(discountCard);
    }

    private DiscountCard getProductFromParams(Map<String, String> params) {
        DiscountCard discountCard = new DiscountCard();
        if (params.containsKey(ApplicationConstants.DISCOUNT_CARD_ID)) {
            Long id = Long.parseLong(params.get(ApplicationConstants.DISCOUNT_CARD_ID));
            discountCard.setId(id);
        }

        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            System.out.println(stringStringEntry.getKey() + " " + stringStringEntry.getValue());
        }
        double discount = Double.parseDouble(params.get(ApplicationConstants.DISCOUNT_CARD_DISCOUNT));
        discountCard.setDiscount(discount);
        return discountCard;
    }
}
