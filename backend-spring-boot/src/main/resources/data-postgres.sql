--https://www.bcryptcalculator.com/encode

-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PATIENT');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN'); -- system administrator
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_SUPPLIER');

-- INSERT ONE ADMIN (system administrator)
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('SystemAdmin', 'Marka Miljanova 7', 'Novi Sad', 'Srbija', 'Milana', 'Todorović', '0601452700');
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('admin@gmail.com', '$2a$10$nshjokILtTylR5sz//j/3OowjgJOtRLk0Z8gI4E2/2QP.VtlDSeEa', true, 1, true);
-- Password for admin account is: administrator
insert into user_authority (user_id, authority_id) values (1, 2);

-- INSERT ONE PATIENT
insert into person (discriminator, address, city, country, name, surname, phone_number,loyalty_points) values ('Patient', 'Jovana Jovanovica 12', 'Novi Sad', 'Srbija', 'Dragana', 'Mororović', '0661352720',50);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('dragana@gmail.com', '$2a$10$Ys8dHFGzcjM.Rn2VbB.rhuSQc/Ye7rawUgWCxE7.kco.x9EE4eYUa', true, 2, false);
-- Password for patient account is: dragana
insert into user_authority (user_id, authority_id) values (2, 1);

-- INSERT ONE PHARMACIST
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('Consultant', 'Jovana Jovanovica 30', 'Novi Sad', 'Srbija', 'Pero', 'Ivanović', '0661352720');
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('pharmacist@gmail.com', '$2a$10$UYKr0EAl1Hhzmg.3LutLeuczdzcdgEwXhuY0raFaNyRRspKTg0MCW', true, 3, true);
-- Password for pharmacist account is: pharmacist
insert into user_authority (user_id, authority_id) values (3, 4);

-- INSERT ONE PATIENT
insert into person (discriminator, address, city, country, name, surname, phone_number,loyalty_points) values ('Patient', 'Pariskih komuna 13', 'Beograd', 'Srbija', 'Milunka', 'Pantic', '0614123355',15);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('milunka@gmail.com', '$2a$10$VN1ORM/2CKQmuFkHTVQEvejLxi9wBl03/RWekn84APbzu31o0LAfa', true, 4, false);
-- Password for patient account is: milunka
insert into user_authority (user_id, authority_id) values (4, 1);

-- INSERT ONE PHARMACIST
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('Consultant', 'Milana Rakica 20', 'Beograd', 'Srbija', 'Vlado', 'Perkic', '06123654523');
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('pharmacist2@gmail.com', '$2a$10$JTbQjDeRv57Hlb/zKRjCUuq348rfmAdoV/Q5KM8ZQ/5LSaCJVJaPW', true, 5, false);
-- Password for pharmacist account is: pharmacist2
insert into user_authority (user_id, authority_id) values (5, 4);

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

-- INSERT LOYALTY PROGRAM
insert into loyalty_program(active_until, is_active, points_per_consultation) values('2021-02-03', true, 15);

-- INSERT USER CATEGORY
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Bronze', 15, 5, 5);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Silver', 30, 10, 10);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Gold', 60, 15, 15);
insert into user_category (loyalty_program_id, name, minimum_points, reservation_discount, consultation_discount) values (1, 'Platinum', 90, 20, 20);


-- INSERT PHARMACIES
insert into pharmacy (dermatologist_consultation_price,pharmacist_consultation_price,latitude,longitude,name,city,country,street, house_number)
values (20,20,19.0,20.0,'Pharmacy Jankovic','Novi Sad','Serbia','Branka Jovica',16);
insert into pharmacy (dermatologist_consultation_price,pharmacist_consultation_price,latitude,longitude,name,city,country,street, house_number)
values (40,50,19.0,20.0,'Pharmacy BENU Mercator','Novi Sad','Serbia','Bulevar Oslobodjenja',102);

-- INSERT PHARMACY RATINGS
insert into pharmacy_rating(rating, patient_id,pharmacy_id) values (5,2,1);
insert into pharmacy_rating(rating, patient_id,pharmacy_id) values (3,2,2);
insert into pharmacy_rating(rating, patient_id,pharmacy_id) values (4,4,2);


insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-03 10:23','2021-02-03 11:00',3,2,1);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-04 11:23','2021-02-04 13:20',3,2,1);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-05 13:23','2021-02-05 14:00',5,4,1);

--INSERT WORKING TIMES
insert into working_times(id,consultant_id,pharmacy_id, monday_start, monday_end, tuesday_start, tuesday_end, wednesday_start,wednesday_end,thursday_start,thursday_end,friday_start,friday_end,saturday_start,saturday_end, sunday_start, sunday_end) values (1,5,1, '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00');
insert into working_times(id,consultant_id,pharmacy_id, monday_start, monday_end, tuesday_start, tuesday_end, wednesday_start,wednesday_end,thursday_start,thursday_end,friday_start,friday_end,saturday_start,saturday_end, sunday_start, sunday_end) values (2,3,1, '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00');

-- INSERT DRUGS
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('lat0x', 5, 'Brufen',1,1);
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('xb0x', 3, 'Aspirin',1,1);
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('0x2x', 6, 'Amfetamin',3,2);
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('3b0a', 7, 'Kreatin',2,3);

--INSERT DRUG RATING
insert into drug_rating(rating,drug_code,patient_id) values (5,'lat0x',1);
insert into drug_rating(rating,drug_code,patient_id) values (4,'xb0x',1);
insert into drug_rating(rating,drug_code,patient_id) values (3,'lat0x',2);
insert into drug_rating(rating,drug_code,patient_id) values (2,'xb0x',2);