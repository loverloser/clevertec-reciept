package ru.clevertec.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.entity.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONParserTest {
    private ObjectMapper objectMapper;
    private JSONParser jsonParser;
    private Product product;
    private DiscountCard discountCard;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        jsonParser = new JSONParser();
        product = new Product(1L, "lemon", 12.99, new ProductProducer(1L, "USA"));
        discountCard = new DiscountCard(1L, 13.5);
    }

    @Test
    @Disabled
    void testParseProductObject() {
        String objectMapperResult = "";
        try {
            objectMapperResult = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String jsonParserResult = jsonParser.parseToString(product);

        assertEquals(objectMapperResult, jsonParserResult);
    }

    @Test
    @Disabled
    void testParseDiscountCardObject() {
        String objectMapperResult = "";
        try {
            objectMapperResult = objectMapper.writeValueAsString(discountCard);
            System.out.println(objectMapperResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String jsonParserResult = jsonParser.parseToString(discountCard);
        System.out.println(jsonParserResult);
        assertEquals(objectMapperResult, jsonParserResult);
    }
}