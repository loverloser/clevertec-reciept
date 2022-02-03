package ru.clevertec.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountCard {

    private Long id;
    private double discount;
}
