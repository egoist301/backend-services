----------------------------------------------------
------------------- BUILD TABLES -------------------
----------------------------------------------------
create table users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
     username VARCHAR(255),
     password VARCHAR(255),
     email VARCHAR(255),
     enabled BOOLEAN DEFAULT TRUE,
     blocking_time VARCHAR(255) NULL
);

create table roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255)
);

create table user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);


INSERT INTO users(id, username, password, email, blocking_time) VALUES (1, 'ADMIN', '{bcrypt}$2a$10$Hp1D3AGqRta6xg/4hq43NOQS.18fZ6IMX.9Hpxw9sEgKiB7Gv1aAy', 'email@email.com', '2023-03-28T11:50:28.483');
INSERT INTO users(id, username, password, email, blocking_time) VALUES (2, 'USER', '{bcrypt}$2a$10$zMIvUGQfFDxE6gqahhLng.XPZX9FJyu2PgvodWmwV56/GyAHMbHmy', 'email@email.com', '2023-03-28T11:50:28.483');
INSERT INTO users(id, username, password, email, blocking_time) VALUES (3, 'USERONE', '{bcrypt}$2a$10$zoj0.5ok/j4aaPvDhhFZ9uEvkESM9XiDM0j/dkzNUGbGzkoY4zeIm', 'email@email.com', '2023-03-28T11:50:28.483');
INSERT INTO users(id, username, password, email, blocking_time) VALUES (4, 'USERTWO', '{bcrypt}$2a$10$3E9q3YU8esj1Z5Jo7x1PsOeymCfsLCq5uk3C8pMcx/nzss77FEWJ6', 'email@email.com', '2023-03-28T11:50:28.483');

INSERT INTO roles(id, role) VALUES (1, 'VIEW_INFO');
INSERT INTO roles(id, role) VALUES (2, 'VIEW_ADMIN');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (3, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (4, 1);



