drop table if exists brand;
drop table if exists patrimony;
drop table if exists users;

CREATE TABLE brand (
   id                  serial  NOT NULL PRIMARY KEY,
   name                VARCHAR(50) NOT NULL
);

CREATE TABLE patrimony (
   id                  serial NOT NULL PRIMARY KEY,
   name         	   VARCHAR(50) NOT NULL,
   description		   VARCHAR(255) NOT NULL,
   brand_id            BIGINT NOT NULL,
   number_fall         BIGINT NOT NULL,
   FOREIGN KEY (brand_id) REFERENCES brand (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE users (
   id                  serial NOT NULL  PRIMARY KEY,
   name         	   VARCHAR(50) NOT NULL,
   email		   	   VARCHAR(50) NOT NULL,
   password            VARCHAR(50) NOT NULL
);