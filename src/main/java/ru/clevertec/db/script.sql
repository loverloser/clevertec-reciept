create table if not exists products
(
    id    BIGSERIAL primary key,
    name  varchar(64),
    price real not null
);

create table if not exists discount_cards
(
    id        BIGSERIAL primary key,
    card_name varchar(128) not null,
    discount  real         not null
);
