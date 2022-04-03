package ru.clevertec.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.entity.Product;
import ru.clevertec.entity.ProductProducer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getDouble("price"))
                .productProducer(
                        ProductProducer.builder()
                                .id(rs.getLong("producer_id"))
                                .name("producer_name")
                                .build()
                )
                .build();
    }
}
