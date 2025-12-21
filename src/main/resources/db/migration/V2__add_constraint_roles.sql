ALTER TABLE roles DROP COLUMN role_name;

ALTER TABLE roles
    ADD role_name VARCHAR(20)
        CHECK (role_name IN ('OWNER', 'ADMIN', 'CLIENT'));