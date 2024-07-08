Creating Table:

CREATE TABLE ticket (
    id INTEGER NOT NULL AUTO_INCREMENT,
    client_id INTEGER,
   last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(255),
    ticket_code INTEGER,
    title VARCHAR(255),
    PRIMARY KEY (id),
    unique  INDEX index_clientid_ticket_code (client_id, ticket_code)
) ENGINE=InnoDB;
