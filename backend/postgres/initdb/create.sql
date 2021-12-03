create sequence hibernate_sequence start 1 increment 1;
create table ship
(
    id            int8 not null,
    mmsi          int4 not null,
    name          varchar(255),
    public_id     uuid not null,
    ship_type     varchar(255),
    still_tracked boolean,
    tracker_id    int8,
    primary key (id)
);
create table ship_localization
(
    id           int8      not null,
    time         timestamp not null,
    x_coordinate float8    not null,
    y_coordinate float8    not null,
    ship_id      int8,
    primary key (id)
);
create table user_entity
(
    id       int8         not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);
alter table if exists ship
    add constraint UK_v14n0ukoin1h5fhd69jo4on6 unique (public_id);
alter table if exists user_entity
    add constraint UK_2jsk4eakd0rmvybo409wgwxuw unique (username);
alter table if exists ship
    add constraint FKdejhom8g5w0jtei3da657nfg9 foreign key (tracker_id) references user_entity;
alter table if exists ship_localization
    add constraint FKosrck97mogp39hh8bjrcnau3o foreign key (ship_id) references ship;
