package ru.clevertec.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.entity.DiscountCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountCardMapper implements RowMapper<DiscountCard> {

    @Override
    public DiscountCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DiscountCard.builder()
                .id(rs.getObject("id", Long.class))
                .discount(rs.getDouble("discount"))
                .build();
    }
}
