create database simpleRPA default character set utf8 collate utf8_general_ci;
show databases;
create user 'simpleRPA'@'%' identified by '1234';
GRANT ALL PRIVILEGES ON *.* TO 'simpleRPA'@'%';

CREATE TABLE robots
(
    robot_id   bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    address    VARCHAR(30) NOT NULL,
    user       VARCHAR(30) NOT NULL,
    password   VARCHAR(30)  NOT NULL,
    created_at   timestamp(6) NOT NULL,
    updated_at   timestamp(6)  DEFAULT NULL
);

CREATE TABLE job_descriptors
(
    jobdescriptor_id  bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name        VARCHAR(30)  NOT NULL,
    robot_id      bigint NOT NULL,
    is_repeat     TINYINT NOT NULL,
    executed_datetime timestamp(6)  DEFAULT NULL,
    created_at   timestamp(6)  NOT NULL,
    updated_at   timestamp(6) DEFAULT NULL,

    CONSTRAINT fk_job_descriptors_to_robots FOREIGN KEY (robot_id) REFERENCES robots (robot_id) ON DELETE CASCADE
);

CREATE TABLE jobs
(
    job_id        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    jobdescriptor_id   bigint  NOT NULL,
    command VARCHAR(30)  NOT NULL,
    parameter   VARCHAR(30) default NULL,
    activation      TINYINT      NOT NULL,

    CONSTRAINT fk_jobs_to_job_descriptors FOREIGN KEY (jobdescriptor_id) REFERENCES job_descriptors (jobdescriptor_id) ON DELETE CASCADE
);


