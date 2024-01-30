CREATE SCHEMA IF NOT EXISTS `e-shop`;

use `e-shop`;

CREATE TABLE IF NOT EXISTS  country(
    id bigint NOT NULL AUTO_INCREMENT,
    code int NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS `language`(
                         id bigint NOT NULL AUTO_INCREMENT,
                         code varchar(10) NOT NULL,
                         name varchar(255) NOT NULL,
                         PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS `file`(
                     id bigint NOT NULL AUTO_INCREMENT,
                     name varchar(255) NOT NULL,
                     path varchar(500) NOT NULL,
                     PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS `currency`(
                         id bigint NOT NULL AUTO_INCREMENT,
                         name varchar(255) NOT NULL,
                         code varchar(5) NOT NULL,
                         icon varchar(10),
                         PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS `province`(
                         id bigint NOT NULL AUTO_INCREMENT,
                         country_id bigint NOT NULL,
                         code int NOT NULL,
                         name varchar(255) NOT NULL,
                         PRIMARY KEY(id));

ALTER TABLE province ADD CONSTRAINT PROVINCE_COUNTRY_ID_FK FOREIGN KEY(country_id) REFERENCES country(id);

CREATE TABLE IF NOT EXISTS `city`(
                     id bigint NOT NULL AUTO_INCREMENT,
                     province_id bigint NOT NULL,
                     code int NOT NULL,
                     name varchar(255) NOT NULL,
                     PRIMARY KEY(id));

ALTER TABLE city ADD CONSTRAINT CITY_PROVINCE_ID_FK FOREIGN KEY(province_id) REFERENCES province(id);

CREATE TABLE IF NOT EXISTS `rol`(
                    id bigint NOT NULL AUTO_INCREMENT,
                    name varchar(255) NOT NULL,
                    description varchar(255),
                    PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS "user"(
                     id bigint NOT NULL AUTO_INCREMENT,
                     name varchar(255) NOT NULL,
                     surname varchar(255),
                     email varchar(255) NOT NULL,
                     password varchar(500) NOT NULL,
                     creation_username varchar(255),
                     creation_date date,
                     update_username varchar(255),
                     update_date date,
                     PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS `rol_user`(
                         id bigint NOT NULL AUTO_INCREMENT,
                         rol_id bigint NOT NULL,
                         user_id bigint NOT NULL,
                         PRIMARY KEY(id));

ALTER TABLE rol_user ADD CONSTRAINT ROL_USER_ROL_ID_FK FOREIGN KEY(rol_id) REFERENCES rol(id);
ALTER TABLE rol_user ADD CONSTRAINT ROL_USER_USER_ID_FK FOREIGN KEY(user_id) REFERENCES "user"(id);


CREATE TABLE IF NOT EXISTS `user_address`(
                             id bigint NOT NULL AUTO_INCREMENT,
                             user_id bigint NOT NULL,
                             city_id bigint NOT NULL,
                             street varchar(255) NOT NULL,
                             phone int NOT NULL,
                             main tinyint DEFAULT 0,
                             cp int NOT NULL,
                             user_address_name varchar(255) NOT NULL,
                             user_address_surname varchar(255) NOT NULL,
                             observations varchar(2000),
                                 PRIMARY KEY(id));

ALTER TABLE user_address ADD CONSTRAINT USER_ADDRESS_USER_ID_FK FOREIGN KEY(user_id) REFERENCES "user"(id);
ALTER TABLE user_address ADD CONSTRAINT USER_ADDRESS_CITY_ID_FK FOREIGN KEY(city_id) REFERENCES city(id);

CREATE TABLE IF NOT EXISTS `category`(
                         id bigint NOT NULL AUTO_INCREMENT,
                         parent_category_id bigint,
                         name varchar(255) NOT NULL,
                         description varchar(255),
                         PRIMARY KEY(id));

ALTER TABLE category ADD CONSTRAINT CATEGORY_PARENT_CATEGORY_ID_FK FOREIGN KEY(parent_category_id) REFERENCES category(id);


CREATE TABLE IF NOT EXISTS `translation_category`(
                                     id bigint NOT NULL AUTO_INCREMENT,
                                     category_id bigint NOT NULL,
                                     language_id bigint NOT NULL,
                                     name varchar(255) NOT NULL,
                                     description varchar(255),
                                     PRIMARY KEY(id));

ALTER TABLE translation_category ADD CONSTRAINT TRANSLATION_CATEGORY_CATEGORY_ID_FK FOREIGN KEY(category_id) REFERENCES category(id);
ALTER TABLE translation_category ADD CONSTRAINT TRANSLATION_CATEGORY_LANGUAGE_ID_FK FOREIGN KEY(language_id) REFERENCES language(id);

CREATE TABLE IF NOT EXISTS `product`(
                        id bigint NOT NULL AUTO_INCREMENT,
                        category_id bigint NOT NULL,
                        code varchar(255) NOT NULL,
                        name varchar(255) NOT NULL,
                        part_number varchar(255) NOT NULL,
                        stock int NOT NULL DEFAULT 0,
                        PRIMARY KEY(id));

ALTER TABLE product ADD CONSTRAINT PRODUCT_CATEGORY_ID_FK FOREIGN KEY(category_id) REFERENCES category(id);

CREATE TABLE IF NOT EXISTS `translation_product`(
                                    id bigint NOT NULL AUTO_INCREMENT,
                                    language_id bigint NOT NULL,
                                    product_id bigint NOT NULL,
                                    name varchar(255) NOT NULL,
                                    description varchar(2000) NOT NULL,
                                    specifications varchar(4000),
                                    characteristics varchar(4000),
                                    PRIMARY KEY(id));

ALTER TABLE translation_product ADD CONSTRAINT TRANSLATION_PRODUCT_LANGUAGE_ID_FK FOREIGN KEY(language_id) REFERENCES language(id);
ALTER TABLE translation_product ADD CONSTRAINT TRANSLATION_PRODUCT_PRODUCT_ID_FK FOREIGN KEY(product_id) REFERENCES product(id);

CREATE TABLE IF NOT EXISTS `product_file`(
                             id bigint NOT NULL AUTO_INCREMENT,
                             product_id bigint NOT NULL,
                             file_id bigint NOT NULL,
                             PRIMARY KEY(id));

ALTER TABLE product_file ADD CONSTRAINT PRODUCT_FILE_PRODUCT_ID_FK FOREIGN KEY(product_id) REFERENCES product(id);
ALTER TABLE product_file ADD CONSTRAINT PRODUCT_FILE_FILE_ID_FK FOREIGN KEY(file_id) REFERENCES file(id);

CREATE TABLE IF NOT EXISTS `opinion_product`(
                                id bigint NOT NULL AUTO_INCREMENT,
                                user_id bigint NOT NULL,
                                product_id bigint NOT NULL,
                                comment varchar(4000) NOT NULL,
                                assessment int NOT NULL,
                                PRIMARY KEY(id));

ALTER TABLE opinion_product ADD CONSTRAINT OPINION_PRODUCT_PRODUCT_ID_FK FOREIGN KEY(product_id) REFERENCES product(id);
ALTER TABLE opinion_product ADD CONSTRAINT OPINION_PRODUCT_USER_ID_FK FOREIGN KEY(user_id) REFERENCES "user"(id);

CREATE TABLE IF NOT EXISTS `price_product`(
                              id bigint NOT NULL AUTO_INCREMENT,
                              currency_id bigint NOT NULL,
                              product_id bigint NOT NULL,
                              start_date date NOT NULL,
                              end_date date,
                              `order` INT DEFAULT 1,
                              price double NOT NULL,
                              PRIMARY KEY(id));

ALTER TABLE price_product ADD CONSTRAINT PRICE_PRODUCT_PRODUCT_ID_FK FOREIGN KEY(product_id) REFERENCES product(id);
ALTER TABLE price_product ADD CONSTRAINT PRICE_PRODUCT_CURRENCY_ID_FK FOREIGN KEY(currency_id) REFERENCES currency(id);