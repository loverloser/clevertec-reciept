package ru.clevertec.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.entity.ProductProducer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductProducerMapper implements RowMapper<ProductProducer> {

    @Override
    public ProductProducer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductProducer.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
