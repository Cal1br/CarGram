DROP TABLE IF EXISTS users CASCADE;
create table users
(
    user_id          bigserial primary key,
    username         varchar(25),
    password         varchar(255),
    biography        varchar(2000),
    is_online        boolean,
    last_online      Timestamp,
    account_creation Date,
    profile_pic      varchar(50)
);
DROP TABLE IF EXISTS car CASCADE;
create table car
(
    car_id              bigserial primary key,
    owner_id            bigint,
    name                varchar(15),
    model               varchar(25),
    description         varchar(1500),
    horsepower          int4,
    engine_displacement float,
    manufacture_date    Date,
    photo               varchar(50),
    CONSTRAINT FK_Owner FOREIGN KEY (owner_id) references users
);
DROP TABLE IF EXISTS car_mod CASCADE;
create table car_mod
(
    mod_id      bigserial primary key,
    car_id      bigint,
    price       float,
    description varchar(1500),
    mod_picture varchar(10),
    date        Date,
    CONSTRAINT FK_Car FOREIGN KEY (car_id) references car
)