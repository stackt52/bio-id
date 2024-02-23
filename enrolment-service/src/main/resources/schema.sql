CREATE SCHEMA IF NOT EXISTS client;

CREATE TABLE IF NOT EXISTS client.subject
(
    id         UUID PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    sex        CHAR DEFAULT 'M',
    dob        DATE
);

CREATE TABLE IF NOT EXISTS client.auxiliary_id
(
    subject_id UUID,
    type       TEXT NOT NULL,
    value      TEXT NOT NULL,
    CONSTRAINT client_auxiliary_id_client_subject_id_fk
        FOREIGN KEY (subject_id)
            REFERENCES client.subject
            on delete cascade
);