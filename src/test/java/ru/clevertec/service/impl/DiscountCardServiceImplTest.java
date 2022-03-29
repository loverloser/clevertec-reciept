//package ru.clevertec.service.impl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.clevertec.ecxeption.CardNotFoundException;
//import ru.clevertec.entity.DiscountCard;
//import ru.clevertec.repository.interfaces.DiscountCardRepository;
//import ru.clevertec.service.interfaces.DiscountCardService;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class DiscountCardServiceImplTest {
//
//    private DiscountCardRepository discountCardRepository;
//    private DiscountCardService discountCardService;
//    private DiscountCard existsDiscountCard;
//
//    @BeforeEach
//    void setUp() {
//        discountCardRepository = Mockito.mock(DiscountCardRepository.class);
//        discountCardService = new DiscountCardServiceImpl(discountCardRepository);
//        existsDiscountCard = new DiscountCard(1L, 0.1);
//    }
//
//    @Test
//    void findByIdByShouldReturnNull() {
//        Mockito.doReturn(null).when(discountCardRepository).findById(null);
//        assertNull(discountCardService.findById(null));
//    }
//
//    @Test
//    void findByIdByShouldTrowCardNotFoundException() {
//        Mockito.doThrow(CardNotFoundException.class).when(discountCardRepository).findById(-1L);
//        assertThrows(CardNotFoundException.class ,() -> discountCardService.findById(-1L));
//    }
//
//    @Test
//    void findByIdByShouldReturnCard() {
//        Mockito.doReturn(Optional.of(existsDiscountCard)).when(discountCardRepository).findById(1L);
//        Optional<DiscountCard> discountCardOptional = discountCardService.findById(1L);
//        assertEquals(Optional.of(existsDiscountCard), discountCardOptional);
//    }
//}