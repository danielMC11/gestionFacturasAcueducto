ALTER TABLE password_reset_token
    ADD email_status VARCHAR(255);

ALTER TABLE password_reset_token
    ADD saga_id UUID;