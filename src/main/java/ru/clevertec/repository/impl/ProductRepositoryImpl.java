package ru.clevertec.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.Product;
import ru.clevertec.mapper.ProductMapper;
import ru.clevertec.repository.interfaces.ProductRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;

    public static final String GET_ALL_SQL = "select p.id,\n" +
                                             "       p.name,\n" +
                                             "       p.price,\n" +
                                             "       p2.id as producer_id,\n" +
                                             "       p2.name producer_name\n" +
                                             "from products p\n" +
                                             "         JOIN product_producers p2 on p2.id = p.producer_id;";

    public static final String GET_PRODUCT_BY_ID_SQL = "select p.id,\n" +
                                                       "       p.name,\n" +
                                                       "       p.price,\n" +
                                                       "       p2.id as producer_id,\n" +
                                                       "       p2.name producer_name\n" +
                                                       "from products p\n" +
                                                       "         JOIN product_producers p2 on p2.id = p.producer_id\n" +
                                                       "where p.id = ?;";

    public static final String ADD_PRODUCT_SQL = "insert into products (name, price, producer_id)\n" +
                                                 "values (?, ?, ?);";

    public static final String REMOVE_PRODUCT_SQL = "delete\n" +
                                                    "from products\n" +
                                                    "where id = ?;";

    public static final String UPDATE_PRODUCT_SQL = "update products\n" +
                                                    "set name = ?,\n" +
                                                    "    price = ?,\n" +
                                                    "    producer_id = ?\n" +
                                                    "    where id = ?;";

    @Override
    public List<Product> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, productMapper);
    }

    @Override
    public Optional<Product> findById(Long idProduct) {
        return jdbcTemplate.queryForStream(GET_PRODUCT_BY_ID_SQL, productMapper, new Object[]{idProduct})
                .findFirst();
    }

    @Override
    public Optional<Product> addProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_PRODUCT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setLong(3, product.getProductProducer().getId());
            return ps;
        }, keyHolder);

        long id = ((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue();
        product.setId(id);

        return Optional.of(product);
    }

    @Override
    public boolean updateProduct(Long id, Product product) {
        return jdbcTemplate.update(UPDATE_PRODUCT_SQL, product.getName(), product.getPrice(),
                product.getProductProducer().getId(), id) == 1;
    }

    @Override
    public boolean removeProduct(Long idProduct) {
        return jdbcTemplate.update(REMOVE_PRODUCT_SQL, idProduct) == 1;
    }

}
