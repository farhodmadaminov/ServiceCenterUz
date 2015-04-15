# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "COMP" ("ID" SERIAL NOT NULL PRIMARY KEY,"MODE" VARCHAR(254) DEFAULT '' NOT NULL,"CPU" VARCHAR(254) DEFAULT '' NOT NULL,"PRICE_ID" INTEGER NOT NULL);
create table "PRICE" ("ID" SERIAL NOT NULL PRIMARY KEY,"MODE" VARCHAR(254) DEFAULT '' NOT NULL);
alter table "COMP" add constraint "PRICE_ID" foreign key("PRICE_ID") references "PRICE"("ID") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "COMP" drop constraint "PRICE_ID";
drop table "PRICE";
drop table "COMP";

