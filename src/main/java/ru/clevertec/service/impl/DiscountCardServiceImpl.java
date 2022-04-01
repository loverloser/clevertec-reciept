package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.service.interfaces.DiscountCardService;

import java.util.List;
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
    public Optional<DiscountCard> findById(Long idDiscountCard) {
        return discountCardRepository.findById(idDiscountCard);
    }

    @Override
    public boolean updateDiscountCard(Long id, DiscountCard discountCard) throws ServiceException {
        try {
            return discountCardRepository.updateDiscountCard(id, discountCard);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeDiscountCard(Long idDiscountCard) throws ServiceException {
        try {
            return discountCardRepository.removeDiscountCard(idDiscountCard);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<DiscountCard> addDiscountCard(DiscountCard discountCard) {
        return discountCardRepository.addDiscountCard(discountCard);
    }
}
