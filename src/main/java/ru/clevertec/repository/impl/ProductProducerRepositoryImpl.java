package ru.clevertec.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.mapper.ProductProducerMapper;
import ru.clevertec.repository.interfaces.ProductProducerRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductProducerRepositoryImpl implements ProductProducerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductProducerMapper productProducerMapper;

    public static final String GET_PRODUCT_PRODUCER_BY_ID_SQL = "select id, name\n" +
                                                                "from product_producers\n" +
                                                                "where id = ?;";

    public static final String GET_ALL_PRODUCT_PRODUCERS = "select id, name\n" +
                                                           "from product_producers;";

    public static final String ADD_PRODUCT_PRODUCER_SQL = "insert into product_producers(name)" +
                                                          "values (?)";

    public static final String REMOVE_PRODUCT_PRODUCER_SQL = "delete\n" +
                                                             "from product_producers\n" +
                                                             "where id = ?;";

    public static final String UPDATE_PRODUCT_PRODUCER_SQL = "update product_producers\n" +
                                                             "set name = ?\n" +
                                                             "where id = ?;";

    @Override
    public List<ProductProducer> getAll() {
        return jdbcTemplate.query(GET_ALL_PRODUCT_PRODUCERS, productProducerMapper);
    }

    @Override
    public Optional<ProductProducer> findById(Long idProductProducer) {
        return jdbcTemplate.queryForStream(GET_PRODUCT_PRODUCER_BY_ID_SQL, productProducerMapper, idProductProducer)
                .findFirst();
    }

    @Override
    public Optional<ProductProducer> addProductProducer(ProductProducer productProducer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_PRODUCT_PRODUCER_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, productProducer.getName());
            return ps;
        }, keyHolder);

        long id = ((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue();
        productProducer.setId(id);

        return Optional.of(productProducer);
    }

    @Override
    public boolean updateProductProducer(Long id, ProductProducer productProducer) {
        return jdbcTemplate.update(UPDATE_PRODUCT_PRODUCER_SQL, productProducerMapper,
                productProducer.getName(), id) == 1;
    }

    @Override
    public boolean removeProductProducer(Long idProductProducer) {
        return jdbcTemplate.update(REMOVE_PRODUCT_PRODUCER_SQL, productProducerMapper, idProductProducer) == 1;
    }
}
