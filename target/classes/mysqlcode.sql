

CREATE TABLE ticket (
    id INTEGER NOT NULL AUTO_INCREMENT,
    client_id INTEGER,
    last_modified_date TIMESTAMP,
    status VARCHAR(264),
    ticket_code INTEGER,
    title VARCHAR(264),
    PRIMARY KEY (id),
    unique  INDEX index_clientid_ticket_code (client_id, ticket_code)
);


CREATE INDEX client_index ON ticket(client_id);
