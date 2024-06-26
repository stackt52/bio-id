CREATE SCHEMA IF NOT EXISTS client;
CREATE SCHEMA IF NOT EXISTS sys;

CREATE TABLE IF NOT EXISTS client.subject
(
    id          UUID PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    last_name   VARCHAR(100) NOT NULL,
    sex         CHAR DEFAULT 'M',
    dob         DATE
);

CREATE TABLE IF NOT EXISTS sys.system
(
    id   UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS client.auxiliary_id
(
    id               SERIAL PRIMARY KEY,
    subject_id       UUID         NOT NULL,
    type             VARCHAR(50)  NOT NULL,
    value            VARCHAR(100) NOT NULL,
    source_system_id UUID         NOT NULL,

    CONSTRAINT client_auxiliary_id_client_subject_id_fk
        FOREIGN KEY (subject_id)
            REFERENCES client.subject
            ON DELETE CASCADE,
    CONSTRAINT client_auxiliary_source_system_id_sys_system_id_fk
        FOREIGN KEY (source_system_id)
            REFERENCES sys.system
            ON DELETE CASCADE,
    CONSTRAINT client_auxiliary_id_type_value_source_system_id_unique
        UNIQUE (type, value, source_system_id)
);