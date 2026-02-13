ALTER TABLE invoices
    ADD status VARCHAR(255);

ALTER TABLE invoices
    ALTER COLUMN status SET NOT NULL;