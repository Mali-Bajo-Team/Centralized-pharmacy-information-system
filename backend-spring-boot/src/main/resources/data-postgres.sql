-- THIS IS IMPORTANT TO STAY HERE(comment abbout password), BECAUSE OF PROJECT REVIEW!!
-- Passwords are hesed using the BCrypt algorithm
-- Password for miki is: 12345
-- nemanja: 123456
-- vlaksi: 123457
insert into users (email, password, is_active) values ('miki@gmail.com', '$2a$10$VtLBp5ad.5l18CXAjKO/muPK3PqGl4mUjND1X5pMof5pPiozY51Wq', true);
insert into users (email, password, is_active) values ('nemanja@gmail.com', '$2a$10$KVFPzlPc.Fyy.d61ldgcn.S3sKTCSl924HRVlTNwyqnJ4mGXvpP5O', true);
insert into users (email, password, is_active) values ('vlaksi@gmail.com', '$2a$10$unkpLADN8dNtJH0BiLr7bOzxWOUdma/fnnxbGSqfagly2CiAU2PcS', true);

-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN'); -- system administrator
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_CONSULTANT');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_SUPPLIER');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);