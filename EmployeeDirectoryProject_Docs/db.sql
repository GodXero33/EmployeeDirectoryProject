DROP DATABASE IF EXISTS employee_directory_project;
CREATE DATABASE employee_directory_project;
USE employee_directory_project;

CREATE TABLE employee (
	id BIGINT AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	department ENUM('HR', 'IT', 'FINANCE', 'OPERATIONS') NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME,
	is_deleted BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id)
);

CREATE TABLE `user` (
	id BIGINT AUTO_INCREMENT,
	employee_id BIGINT NOT NULL,
	username VARCHAR(15) NOT NULL,
	password VARCHAR(255) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME,
	last_login DATETIME,
	deleted_at DATETIME,
	is_deleted BOOLEAN DEFAULT FALSE,
	role ENUM('ADMIN') NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (employee_id) REFERENCES employee(id)
);

DESC employee;
DESC `user`;

INSERT INTO employee (name, email, department)
VALUES
('Sathish Shan', 'shansathish38@gmail.com', 'IT'),
('Sahan Perera', 'sahan.perera@example.com', 'IT'),
('Nimesha Fernando', 'nimesha.fernando@example.com', 'HR'),
('Tharindu Silva', 'tharindu.silva@example.com', 'FINANCE'),
('Ishara Jayasinghe', 'ishara.jaya@example.com', 'OPERATIONS'),
('Dilani Wickramasinghe', 'dilani.wick@example.com', 'IT');

INSERT INTO `user` (employee_id, username, password, role)
VALUES
(1, 'god_xero', '$2a$12$m8klimGVexhjMpucFB0UFOxnnaiWXFf26BH9rk/ZVaxa/RwblMAva', 'ADMIN'),
(2, 'sahan_p', '$2a$12$m8klimGVexhjMpucFB0UFOxnnaiWXFf26BH9rk/ZVaxa/RwblMAva', 'ADMIN'),
(3, 'nimesha_f', '$2a$12$m8klimGVexhjMpucFB0UFOxnnaiWXFf26BH9rk/ZVaxa/RwblMAva', 'ADMIN'),
(4, 'tharindu_s', '$2a$12$m8klimGVexhjMpucFB0UFOxnnaiWXFf26BH9rk/ZVaxa/RwblMAva', 'ADMIN'),
(5, 'ishara_j', '$2a$12$m8klimGVexhjMpucFB0UFOxnnaiWXFf26BH9rk/ZVaxa/RwblMAva', 'ADMIN'),
(6, 'dilani_w', '$2a$12$m8klimGVexhjMpucFB0UFOxnnaiWXFf26BH9rk/ZVaxa/RwblMAva', 'ADMIN');

SELECT * FROM employee;
SELECT * FROM `user`;
