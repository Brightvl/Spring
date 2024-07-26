CREATE TABLE project (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL
);

CREATE TABLE timesheet (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           projectId BIGINT,
                           minutes INT,
                           createdAt DATE
);
