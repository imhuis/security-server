create schema security_db collate utf8mb4_0900_ai_ci;

-- user table
create table s_user
(
    id          bigint auto_increment
        primary key,
    uid         varchar(50)  null,
    username    varchar(30)  null,
    email       varchar(30)  null,
    phone       varchar(11)  null,
    pwd         varchar(100) null,
    salt_key    varchar(100) null,
    nickname    varchar(20)  null,
    create_time timestamp    null,
    update_time timestamp    null
);

create index s_user_email_index
    on s_user (email);

create index s_user_phone_index
    on s_user (phone);

create index s_user_username_index
    on s_user (username);
