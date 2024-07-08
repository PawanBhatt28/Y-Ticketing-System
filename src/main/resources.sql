CREATE TABLE ticket (
    id INTEGER NOT NULL AUTO_INCREMENT,
    client_id INTEGER,
    last_modified_date DATETIME(6),
    status VARCHAR(255),
    ticket_code INTEGER,
    title VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY (ticket_code),
    unique  INDEX index_clientid_ticket_code (client_id, ticket_code)
) ENGINE=InnoDB;