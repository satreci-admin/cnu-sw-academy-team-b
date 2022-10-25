INSERT INTO robots (created_at, update_at, address, password, user)
VALUES(
          CURRENT_TIMESTAMP,
          CURRENT_TIMESTAMP,
          "127.0.0.1:22",
          "1234",
          "anonymous"
);

INSERT INTO job_descriptors (created_at, update_at, is_repeat, name, robot_id)
VALUES (
                   CURRENT_TIMESTAMP,
                   CURRENT_TIMESTAMP,
                   false,
                   "즉시실행할 작업명세서",
                   1
       );

INSERT INTO job_descriptors (created_at, update_at, executed_datetime, is_repeat, name, robot_id)
VALUES (
                   CURRENT_TIMESTAMP,
                   CURRENT_TIMESTAMP,
                   timestamp("2022-10-20 17:00:00"),
                   false,
                   "예약실행할 작업명세서",
                   1
       );

INSERT INTO job_descriptors (created_at, update_at, executed_datetime, is_repeat, name, robot_id)
VALUES (
                   CURRENT_TIMESTAMP,
                   CURRENT_TIMESTAMP,
                   timestamp("2022-10-20 17:00:00"),
                   true,
                   "매일 실행할 작업명세서",
                   1
       );

INSERT INTO jobs (activation, command, jobdescriptor_id, parameter)
VALUES (
            false,
            "ls",
            1,
            "-al"
       );

INSERT INTO jobs (activation, command, jobdescriptor_id, parameter)
VALUES (
           false,
           "ls",
           2,
           "-al"
       );

INSERT INTO jobs (activation, command, jobdescriptor_id, parameter)
VALUES (
           false,
           "ls",
           3,
           "-al"
       );