ALTER TABLE users
    ADD CONSTRAINT check_user_role
        CHECK (role IN ('OWNER', 'ADMIN', 'CLIENT'));

ALTER TABLE invoices
    ADD CONSTRAINT check_invoice_status
        CHECK (status IN ('PENDING', 'DATED', 'SUSPENDED', 'CANCELED'));