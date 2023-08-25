create table IF NOT EXISTS products
(
    id              serial primary key,
    created_at      timestamp,
    updated_at      timestamp,
    category        varchar(255)   not null,
    description     varchar(255)   not null,
    name            varchar(255)   not null,
    price           numeric(19, 2) not null,
    production_date date           not null
);

create table IF NOT EXISTS roles
(
    id   serial primary key,
    role varchar(255) not null
        constraint uk_g50w4r0ru3g9uf6i6fr4kpro8
            unique
);


create table IF NOT EXISTS users
(
    id           serial primary key,
    created_at   timestamp,
    updated_at   timestamp,
    age          integer      not null,
    code         varchar(255) not null,
    day_of_birth date         not null,
    email        varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    gender       varchar(255) not null,
    is_verified  boolean      not null,
    lastname     varchar(255) not null,
    middle_name  varchar(255),
    name         varchar(255) not null,
    password     varchar(255) not null,
    phone        varchar(255) not null
        constraint uk_du5v5sr43g5bfnji4vb8hg5s3
            unique,
    username     varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique,
    role_id      integer      not null
        constraint fkp56c1712k691lhsyewcssf40f
            references roles
);


create table IF NOT EXISTS tokens
(
    id      serial primary key,
    token   varchar(255) not null
        constraint uk_na3v9f8s7ucnj16tylrs822qj
            unique,
    user_id integer      not null
        constraint fk2dylsfo39lgjyqml2tbe0b0ss
            references users
);

create table basket
(
    id         serial primary key,
    count      integer not null,
    product_id integer not null
        constraint fkpsbpg5lc7uk06uixvl9ehdkvt
            references products,
    user_id    integer not null
        constraint fk810a8gq30miyp6j1eub97qm6k
            references users
);

create table orders
(
    id           serial primary key,
    created_at   timestamp,
    updated_at   timestamp,
    note         varchar(255),
    status       varchar(255)   not null,
    total_amount numeric(19, 2) not null,
    total_count  integer        not null,
    user_id      integer        not null
        constraint fk32ql8ubntj5uh44ph9659tiih
            references users
);

create table order_items
(
    id          serial primary key,
    created_at  timestamp,
    updated_at  timestamp,
    product_id  integer   not null,
    count       integer        not null,
    total_price numeric(19, 2) not null,
    order_id    integer        not null
        constraint fkbioxgbv59vetrxe0ejfubep1w
            references orders
);

