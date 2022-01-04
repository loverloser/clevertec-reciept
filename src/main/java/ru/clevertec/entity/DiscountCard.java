package ru.clevertec.entity;


import java.util.Objects;

public class DiscountCard {
    private String name;
    private double discount;

    public DiscountCard() {
    }

    public DiscountCard(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
               "name='" + name + '\'' +
               ", discount=" + discount +
               '}';
    }
}
