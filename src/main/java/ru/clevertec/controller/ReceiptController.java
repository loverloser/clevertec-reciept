package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factory.DiscountCardFactory;
import ru.clevertec.factory.ProductFactory;
import ru.clevertec.print.impl.ReceiptPDFPrinter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/receipt")
@RestController
public class ReceiptController {

    private final DiscountCardFactory discountCardFactory;
    private final ProductFactory productFactory;

    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public byte[] printPDF(@RequestBody List<String> list){
        DiscountCard discountCard = discountCardFactory.getInstance(list.toArray(new String[0]));
        Map<Product, Integer> products = productFactory.getInstance(list.toArray(new String[0]));
        return new ReceiptPDFPrinter().print(products, discountCard).getBytes(StandardCharsets.UTF_8);
    }
}
