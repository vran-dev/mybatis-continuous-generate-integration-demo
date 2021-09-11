create database if not exists mybatis_continuous_generator_demo;
create table if not exists user
(
    id        bigint auto_increment primary key comment 'id',
    username  varchar(512) not null comment '用户名',
    gender    varchar(64)  not null default 'MALE' comment '性别',
    create_at timestamp    not null default current_timestamp,
    update_at timestamp    not null default current_timestamp on update current_timestamp
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
