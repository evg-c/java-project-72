DROP TABLE IF EXISTS urls CASCADE;

CREATE TABLE urls (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT pk_url PRIMARY KEY (id)
);

DROP TABLE IF EXISTS url_checks CASCADE;

CREATE TABLE url_checks (
    id SERIAL NOT NULL,
    status_code INT,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description TEXT,
    url_id INT NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT pr_url_checks PRIMARY KEY (id),
    CONSTRAINT fk_url_checks FOREIGN KEY (url_id) REFERENCES urls(id) ON DELETE CASCADE
)