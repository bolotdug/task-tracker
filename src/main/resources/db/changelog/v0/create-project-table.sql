CREATE TABLE project
(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    start_date TIMESTAMP ,
    completion_date TIMESTAMP ,
    status TEXT ,
    priority INT
)