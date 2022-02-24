CREATE TABLE IF NOT EXISTS producers
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) not null
);

insert into producers (name)
values ('UAS'),
       ('Ukraine'),
       ('Mexico');

create table if not exists products
(
    id          bigserial
        constraint products_pkey
            primary key,
    name        varchar(64) not null,
    price       real        not null,
    producer_id integer     not null
        constraint producer_id
            references producers
);

insert into products (name, price, producer_id)
values ('Apple', 12.11, 1),
       ('Orange', 12.33, 2),
       ('Lemon', 0.99, 3),
       ('Pine', 3.99, 1),
       ('Pineapple', 2.19, 2),
       ('Milk', 2.19, 1),
       ('Pineapple', 2.19, 1),
       ('Pineapple', 2.19, 1);

create table if not exists discount_cards
(
    id       BIGSERIAL primary key,
    discount real not null
);

insert into discount_cards (discount)
values (0.1),
       (0.3),
       (0.7);


-- Написать запрос для поиска товара с названием начинающимся на “А”
select p.id,
       p.name,
       p.price,
       p2.id as producer_id,
       p2.name  producer_name
from products p
         JOIN producers p2 on p2.id = p.producer_id
where p.name ILIKE 'a%';

-- Написать запрос с вложенным запросом
select id, name, price
from products
where price < (select avg(price) from products);

-- Join 2 tables
select p.id,
       p.name,
       p.price,
       p2.id as producer_id,
       p2.name  producer_name
from products p
         JOIN producers p2 on p2.id = p.producer_id;

-- Написать запрос с группировкой данных и последующей фильтрацией
select p2.name              producer_name,
       count(p.producer_id) count
from products p
         JOIN producers p2 on p2.id = p.producer_id
group by p2.name, p.producer_id
having count(p.producer_id) > 1;


