create table users(
    id bigint primary key auto_increment,
    active bit(1),
    date_of_created datetime(6),
    password varchar(1000),
    country varchar(255),
    lastname varchar(255),
    name varchar(255),
    role enum('ROLE_ADMIN','ROLE_USER'),
    username varchar(255),
    age int,
    avatar_id bigint
);

create table user_news(
    news_id bigint not null,
    user_id bigint not null
);

create table user_image(
    image_id bigint not null,
    user_id bigint not null
);

create table user_avatar(
    avatar_id bigint,
    user_id bigint
);

create table news(
    id bigint primary key auto_increment,
    topic varchar(255),
    date_of_creating datetime,
    writer_id bigint,
    image_id bigint
);

create table news_like(
    news_id bigint,
    user_id bigint
);

create table news_comment(
    news_id bigint,
    comment_id bigint
);

create table chat_member(
    chat_id bigint,
    user_id bigint
);

create table chats(
    id bigint primary key auto_increment
);

create table messages(
    id bigint primary key auto_increment,
    chat_id bigint,
    text text not null,
    sender_id bigint
);

create table comments(
    id bigint primary key auto_increment,
    sender_id bigint,
    message text not null
);

create table friendship(
    user_id bigint,
    friend_id bigint,
    chat_id bigint
);

create table images(
    id bigint primary key auto_increment,
    path text,
    date_of_creating datetime
);

create table messages_seq(
    next_val bigint
);