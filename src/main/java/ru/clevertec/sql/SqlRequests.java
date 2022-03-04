package ru.clevertec.sql;

public final class SqlRequests {

    public static final String GET_ALL_PRODUCTS = "select p.id,\n" +
                                                  "       p.name,\n" +
                                                  "       p.price,\n" +
                                                  "       p2.id as producer_id,\n" +
                                                  "       p2.name producer_name\n" +
                                                  "from products p\n" +
                                                  "         JOIN producers p2 on p2.id = p.producer_id;";

    public static final String GET_PRODUCT_BY_ID ="select p.id,\n" +
                                                  "       p.name,\n" +
                                                  "       p.price,\n" +
                                                  "       p2.id as producer_id,\n" +
                                                  "       p2.name producer_name\n" +
                                                  "from products p\n" +
                                                  "         JOIN producers p2 on p2.id = p.producer_id\n" +
                                                  "where p.id = ?;";

    public static final String ADD_PRODUCT = "insert into products (name, price, producer_id)\n" +
                                             "values (?, ?, ?);";

    public static final String REMOVE_PRODUCT = "delete\n" +
                                                "from products\n" +
                                                "where id = ?;";

    public static final String UPDATE_PRODUCT = "update products\n" +
                                                "set name = ?,\n" +
                                                "    price = ?,\n" +
                                                "    producer_id = ?\n" +
                                                "    where id = ?;";

    public static final String GET_DISCOUNT_CARD_BY_ID = "select id, discount\n" +
                                                         "from discount_cards\n" +
                                                         "where id = ?;";

}
