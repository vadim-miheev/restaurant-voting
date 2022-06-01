CREATE TABLE users
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR                   NOT NULL,
    login            VARCHAR                   NOT NULL,
    password         VARCHAR                   NOT NULL
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);