create table widget
(
    id                bigint  not null
        constraint widget_pkey
            primary key,
    creation_time     timestamp,
    modification_time timestamp,
    version           integer not null,
    x                 integer,
    y                 integer,
    z_index           integer,
    width             integer,
    height            integer
);