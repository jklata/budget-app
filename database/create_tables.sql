#TODO: uzupełnić wszędzie constraints oraz braki
drop table if exists payment_category;
create table payment_category
(
    id    bigint       not null
        primary key,
    name  varchar(255) not null,
    color varchar(255) not null
);


drop table if exists user;
create table user
(
    id         bigint       not null
        primary key,
    login      varchar(255) not null,
    password   varchar(255) not null,
    email      varchar(255) not null,
    avatar     blob,
    first_name varchar(255) not null,
    last_name  varchar(255) not null
#   lista account
);

drop table if exists accout;
create table account
(
    id              bigint not null
        primary key,
    name            varchar(255),
    currency        varchar(255),
    initial_balance decimal(13, 2),
#   user
    account_type    enum ('BANK', 'CASH', 'SAVING')
#   lista payments
);

drop table if exists payment;
create table payment
(
    id               bigint         not null
        primary key,
    transaction_date date           not null,
    insert_date      date           not null,
    amount           decimal(13, 2) not null,
    title            varchar(255)   not null,
    #paymentcategory
    payment_type     enum ('EXPENSE', 'INCOME')
    #budget
    #set hashtagow
    #account

);

drop table if exists budget;
create table budget
(
    id           bigint         not null
        primary key,
    name         varchar(255)   not null,
    budget_value decimal(13, 2) not null
    # lista payments
);

drop table if exists hashtag;
create table hashtag
(
    id   bigint       not null
        primary key,
    name varchar(255) not null
    #lista payments
);

