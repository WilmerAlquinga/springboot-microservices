/*==============================================================*/
/* Table: ACCOUNT                                               */
/*==============================================================*/
CREATE TABLE account
(
    account_id         SERIAL      NOT NULL,
    client_id          INT4        NOT NULL,
    account_number     VARCHAR(16) NOT NULL UNIQUE,
    account_type       VARCHAR(16) NULL,
    account_balance    NUMERIC(12, 2)
        CONSTRAINT CKC_BALANCE_TEST CHECK (account_balance >= 0.00),
    account_status     BOOL DEFAULT FALSE,
    account_created_at TIMESTAMP,
    account_created_by VARCHAR(128),
    account_updated_at TIMESTAMP,
    account_updated_by VARCHAR(128),
    account_deleted_at TIMESTAMP   NULL,
    CONSTRAINT PK_account PRIMARY KEY (account_id)
);

COMMENT
    ON TABLE account IS 'Table for storing all accounts in the application';

/*==============================================================*/
/* Table: MOVEMENT                                              */
/*==============================================================*/
CREATE TABLE movement
(
    movement_id         SERIAL         NOT NULL,
    account_id          INT4           NOT NULL,
    movement_date       TIMESTAMP      NOT NULL,
    movement_type       VARCHAR(16)    NOT NULL,
    movement_amount     NUMERIC(12, 2) NOT NULL,
    movement_balance    NUMERIC(12, 2) NOT NULL,
    movement_created_at TIMESTAMP,
    movement_created_by VARCHAR(128),
    movement_updated_at TIMESTAMP,
    movement_updated_by VARCHAR(128),
    movement_deleted_at TIMESTAMP      NULL,
    CONSTRAINT PK_movement PRIMARY KEY (movement_id)
);

COMMENT
    ON TABLE movement IS 'Table for storing all movements in the application';

ALTER TABLE movement
    ADD CONSTRAINT fk_movement_reference_account FOREIGN KEY (account_id)
        REFERENCES account (account_id)
        ON DELETE RESTRICT;
