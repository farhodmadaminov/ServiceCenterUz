# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "COMP" ("ID" SERIAL NOT NULL PRIMARY KEY,"MODE" VARCHAR(254) DEFAULT '' NOT NULL,"CPU" VARCHAR(254) DEFAULT '' NOT NULL,"MARK" DOUBLE PRECISION DEFAULT 0.0 NOT NULL);

# --- !Downs

drop table "COMP";

