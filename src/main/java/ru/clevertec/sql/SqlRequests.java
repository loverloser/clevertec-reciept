package ru.clevertec.sql;

public final class SqlRequests {

    public static final String GET_ALL_PRODUCTS = "select id, name, price\n" +
                                                  "from products;";

    public static final String GET_PRODUCT_BY_ID = "select id, name, price\n" +
                                                   "from products\n" +
                                                   "where id = ?;";

    public static final String ADD_PRODUCT = "insert into products (name, price)\n" +
                                             "values (?, ?);";

    public static final String REMOVE_PRODUCT = "delete\n" +
                                                "from products\n" +
                                                "where id = ?;";

    public static final String UPDATE_PRODUCT = "update products\n" +
                                                "set name  = ?,\n" +
                                                "    price = ?\n" +
                                                "where id = ?;";

    public static final String GET_DISCOUNT_CARD_BY_ID = "select id, discount\n" +
                                                         "from discount_cards\n" +
                                                         "where id = ?;";

}
