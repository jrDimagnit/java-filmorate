CREATE TABLE IF NOT EXISTS MPA(
    MPA_id int not null ,
    mpa_name varchar(20) not null ,
        constraint MPA_PK primary key (MPA_id)
);

CREATE TABLE IF NOT EXISTS FILMS(
    film_id integer auto_increment,
    name varchar(200) not null ,
    description varchar(200) not null ,
    release_date date not null ,
    duration int not null ,
    rating_id int not null,
       constraint FILMS_PK primary key (film_id),
       constraint RATING_ID foreign key (rating_id) references MPA(mpa_id)
);

CREATE TABLE IF NOT EXISTS USERS(
    user_id integer auto_increment,
    name varchar (200) not null ,
    email varchar (200) not null ,
    login varchar (200) not null ,
    birthdate date not null,
        constraint USERS_PK primary key (user_id)
);

CREATE TABLE IF NOT EXISTS GENRES(
    genre_id int not null,
    name varchar(20) not null,
    constraint GENRES_PK primary key (genre_id)
);

CREATE TABLE IF NOT EXISTS FILM_GENRES(
    film_id int not null ,
    id_genre int not null,
        constraint FILM_GENRES_PK primary key (film_id,id_genre)
);

CREATE TABLE IF NOT EXISTS FRIENDS(
    user_id int not null ,
    friend_id int not null ,
    constraint FRIENDS_PK primary key (user_id,friend_id),
    constraint USER_ID_FK foreign key (user_id) references  USERS(user_id),
    constraint FRIEND_ID_FK foreign key (friend_id) references  USERS(user_id)
);

CREATE TABLE IF NOT EXISTS FILM_LIKES(
    film_id int not null,
    user_id int not null ,
    constraint FILM_LIKES_PK primary key (film_id,user_id)
);
