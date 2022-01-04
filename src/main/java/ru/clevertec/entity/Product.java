package ru.clevertec.entity;

import java.util.Objects;

public class Product {
    private int id;
    private String description;
    private double price;
    private int count;
    private double total;

    public Product() {
    }

    public Product(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Product(int id, String description, double price, int count) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotal() {
        double total = this.price * count;
        if (count > 5) {
            total -= total * 0.1;
        }
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && description.equals(product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", count=" + count +
               ", total=" + total +
               '}';
    }


}
