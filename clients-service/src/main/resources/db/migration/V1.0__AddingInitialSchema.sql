/*==============================================================*/
/* Table: PERSON                                                */
/*==============================================================*/
CREATE TABLE person
(
    person_id              INT          NOT NULL AUTO_INCREMENT,
    person_name            VARCHAR(128) NOT NULL,
    person_gender          VARCHAR(32)  NOT NULL,
    person_age             INT          NOT NULL,
    person_document_number VARCHAR(16)  NOT NULL,
    person_address         VARCHAR(128),
    person_phone_number    VARCHAR(32),
    person_created_at      TIMESTAMP    NOT NULL,
    person_created_by      VARCHAR(64),
    person_updated_at      TIMESTAMP    NOT NULL,
    person_updated_by      VARCHAR(64),
    person_deleted_at      TIMESTAMP    NULL,
    PRIMARY KEY (person_id)
);

ALTER TABLE person COMMENT 'Table for storing all persons in the application';

/*==============================================================*/
/* Table: CLIENT                                                */
/*==============================================================*/
CREATE TABLE client
(
    client_id         INT          NOT NULL AUTO_INCREMENT,
    person_id         INT          NOT NULL,
    client_password   VARCHAR(512) NOT NULL,
    client_status     VARCHAR(16)  NOT NULL,
    client_created_at TIMESTAMP    NOT NULL,
    client_created_by VARCHAR(64),
    client_updated_at TIMESTAMP    NOT NULL,
    client_updated_by VARCHAR(64),
    client_deleted_at TIMESTAMP    NULL,
    PRIMARY KEY (client_id)
);

ALTER TABLE client COMMENT 'Table for storing all clients in the application';

ALTER TABLE client
    ADD CONSTRAINT fk_client_reference_person FOREIGN KEY (person_id)
        REFERENCES person (person_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
