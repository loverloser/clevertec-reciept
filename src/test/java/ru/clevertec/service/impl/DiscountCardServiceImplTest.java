//package ru.clevertec.service.impl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.clevertec.repository.DiscountCardRepository;
//import ru.clevertec.service.DiscountCardService;
//
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//class DiscountCardServiceImplTest {
//
//    private DiscountCardRepository discountCardRepository;
//    private DiscountCardService discountCardService;
//
//    @BeforeEach
//    void setUp() {
//        discountCardRepository = Mockito.mock(DiscountCardRepository.class);
//        discountCardService = new DiscountCardServiceImpl(discountCardRepository);
//    }
//
//    @Test
//    void findByIdByShouldReturnNull() {
//        Mockito.doReturn(null).when(discountCardRepository).findById(null);
//        assertNull(discountCardService.findById(null));
//    }
//}