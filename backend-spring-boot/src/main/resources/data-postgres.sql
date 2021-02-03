-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PATIENT');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN'); -- system administrator
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_SUPPLIER');

-- INSERT ONE ADMIN (system administrator)
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('SystemAdmin', 'Marka Miljanova 7', 'Novi Sad', 'Srbija', 'Milana', 'Todorović', '0601452700');
insert into user_account (email, password, is_active, person_id) values ('admin@gmail.com', '$2a$10$nshjokILtTylR5sz//j/3OowjgJOtRLk0Z8gI4E2/2QP.VtlDSeEa', true, 1);
-- Password for admin account is: administrator
insert into user_authority (user_id, authority_id) values (1, 2);

-- INSERT ONE PATIENT
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('Patient', 'Jovana Jovanovica 12', 'Novi Sad', 'Srbija', 'Dragna', 'Mororović', '0661352720');
insert into user_account (email, password, is_active, person_id) values ('dragana@gmail.com', '$2a$10$Ys8dHFGzcjM.Rn2VbB.rhuSQc/Ye7rawUgWCxE7.kco.x9EE4eYUa', true, 2);
-- Password for patient account is: dragana
insert into user_authority (user_id, authority_id) values (2, 1);

-- INSERT ONE PHARMACIST
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('Consultant', 'Jovana Jovanovica 30', 'Novi Sad', 'Srbija', 'Pero', 'Ivanović', '0661352720');
insert into user_account (email, password, is_active, person_id) values ('pharmacist@gmail.com', '$2a$10$UYKr0EAl1Hhzmg.3LutLeuczdzcdgEwXhuY0raFaNyRRspKTg0MCW', true, 3);
-- Password for pharmacist account is: pharmacist
insert into user_authority (user_id, authority_id) values (3, 4);

insert into drug_form (name) values ('Capsule');
insert into drug_form (name) values ('Tablet');
insert into drug_form (name) values ('Injection');
insert into drug_form (name) values ('Syrup');
insert into drug_form (name) values ('Suspension');
insert into drug_form (name) values ('Cream');

insert into drug_class (name) values ('Antibiotic');
insert into drug_class (name) values ('Analgesic');
insert into drug_class (name) values ('Anticoagulant');
insert into drug_class (name) values ('Antiviral');
insert into drug_class (name) values ('Cardiovascular');

insert into loyalty_program (is_active, points_per_consultation) values (true, 5);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Bronze', 15, 5, 5);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Silver', 30, 10, 10);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Gold', 60, 15, 15);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Platinum', 90, 20, 20);


insert into pharmacy (id,dermatologist_consultation_price,pharmacist_consultation_price,latitude,longitude,name) values (1,20,20,19.0,20.0,'Jankovic');

insert into consultation (id,price,status,end_date, start_date,consultant_id,patient_id,pharmacy_id) values (1,20,1,'2020-05-05 10:23','2020-05-05 11:00',1,2,1);

