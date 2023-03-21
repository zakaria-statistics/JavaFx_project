create database db_product;

use db_product;

create table CATEGORY(
         categoryId BIGINT NOT NULL primary key AUTO_INCREMENT,
         name varchar(100)
);

create table PRODUCT(
    productId BIGINT NOT NULL primary key AUTO_INCREMENT,
    name varchar(100),
    ref varchar(100),
    price decimal,
    categoryId BIGINT NOT NULL ,
    foreign key (categoryId) references CATEGORY (categoryId)
);

