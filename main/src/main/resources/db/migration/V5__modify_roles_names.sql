ALTER TABLE users
    ADD CONSTRAINT check_user_role
        CHECK (role IN ('OWNER', 'ADMIN', 'SUBSCRIBER'));