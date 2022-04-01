package ru.clevertec.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.mapper.DiscountCardMapper;
import ru.clevertec.repository.interfaces.DiscountCardRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DiscountCardMapper discountCardMapper;

    public static final String GET_DISCOUNT_CARDS_SQL = "select id, discount\n" +
                                                        "from discount_cards;";

    public static final String GET_DISCOUNT_CARD_BY_ID_SQL = "select id, discount\n" +
                                                             "from discount_cards\n" +
                                                             "where id = ?;";

    public static final String ADD_DISCOUNT_CARD_SQL = "insert into discount_cards(discount)" +
                                                       "values(?);";

    public static final String REMOVE_DISCOUNT_CARD_SQL = "delete\n" +
                                                          "from discount_cards\n" +
                                                          "where id = ?;";

    public static final String UPDATE_DISCOUNT_CARD_SQL = "update discount_cards\n" +
                                                          "set discount = ?\n" +
                                                          "where id = ?;";

    @Override
    public List<DiscountCard> getAll() {
        return jdbcTemplate.query(GET_DISCOUNT_CARDS_SQL, discountCardMapper);
    }

    @Override
    public Optional<DiscountCard> findById(Long idDiscountCard) {
        return jdbcTemplate.queryForStream(GET_DISCOUNT_CARD_BY_ID_SQL, discountCardMapper,
                        new Object[]{idDiscountCard})
                .findFirst();
    }

    @Override
    public Optional<DiscountCard> addDiscountCard(DiscountCard discountCard) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_DISCOUNT_CARD_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, discountCard.getDiscount());
            return ps;
        }, keyHolder);

        Long id = (Long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
        discountCard.setId(id);

        return Optional.of(discountCard);
    }

    @Override
    public boolean updateDiscountCard(Long idDiscountCard, DiscountCard discountCard) {
        return jdbcTemplate.update(UPDATE_DISCOUNT_CARD_SQL, discountCard.getDiscount(), idDiscountCard) == 1;
    }

    @Override
    public boolean removeDiscountCard(Long idDiscountCard) {
        return jdbcTemplate.update(REMOVE_DISCOUNT_CARD_SQL, idDiscountCard) == 1;
    }

}
