-- bcrypt: https://www.bcryptcalculator.com

-- All roles need to have prefix ROLE_ !!
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PATIENT');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN'); -- system administrator
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_SUPPLIER');

-- INSERT ONE ADMIN (system administrator)
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('SystemAdmin', 'Marka Miljanova 7', 'Novi Sad', 'Srbija', 'Milana', 'Todorović', '0601452700');
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+admin@gmail.com', '$2a$10$nshjokILtTylR5sz//j/3OowjgJOtRLk0Z8gI4E2/2QP.VtlDSeEa', true, 1, false);
-- Password for admin account is: administrator
insert into user_authority (user_id, authority_id) values (1, 2);

-- INSERT ONE PATIENT
insert into person (discriminator, address, city, country, name, surname, phone_number,loyalty_points) values ('Patient', 'Jovana Jovanovica 12', 'Novi Sad', 'Srbija', 'Dragana', 'Mororović', '0661352720',50);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+dragana@gmail.com', '$2a$10$Ys8dHFGzcjM.Rn2VbB.rhuSQc/Ye7rawUgWCxE7.kco.x9EE4eYUa', true, 2, false);
-- Password for patient account is: dragana
insert into user_authority (user_id, authority_id) values (2, 1);

-- INSERT ONE PHARMACIST
insert into person (discriminator, address, city, country, name, surname, phone_number, type) values ('Consultant', 'Jovana Jovanovica 30', 'Novi Sad', 'Srbija', 'Pero', 'Ivanović', '0661352720', 0);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+pharmacist@gmail.com', '$2a$10$UYKr0EAl1Hhzmg.3LutLeuczdzcdgEwXhuY0raFaNyRRspKTg0MCW', true, 3, false);
-- Password for pharmacist account is: pharmacist
insert into user_authority (user_id, authority_id) values (3, 4);

-- INSERT ONE PATIENT
insert into person (discriminator, address, city, country, name, surname, phone_number,loyalty_points) values ('Patient', 'Pariskih komuna 13', 'Beograd', 'Srbija', 'Milunka', 'Pantic', '0614123355',15);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('scpisuser+milunka@gmail.com', '$2a$10$VN1ORM/2CKQmuFkHTVQEvejLxi9wBl03/RWekn84APbzu31o0LAfa', true, 4, false);
-- Password for patient account is: milunka
insert into user_authority (user_id, authority_id) values (4, 1);

-- INSERT ONE PHARMACIST
insert into person (discriminator, address, city, country, name, surname, phone_number, type) values ('Consultant', 'Milana Rakica 20', 'Beograd', 'Srbija', 'Vlado', 'Perkic', '06123654523', 0);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+pharmacist2@gmail.com', '$2a$10$JTbQjDeRv57Hlb/zKRjCUuq348rfmAdoV/Q5KM8ZQ/5LSaCJVJaPW', true, 5, false);
-- Password for pharmacist account is: pharmacist2
insert into user_authority (user_id, authority_id) values (5, 4);

--INSERT DRUG FORM & CLASS
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
insert into pharmacy (dermatologist_consultation_price,pharmacist_consultation_price,latitude,longitude,name,city,country,street, description)
values (20, 20, 45.267136, 19.83694,'Pharmacy Jankovic','Novi Sad','Serbia','Branka Jovica 16', 'Najbolja apoteka u gradu!'); -- id 1
insert into pharmacy (dermatologist_consultation_price,pharmacist_consultation_price,latitude,longitude,name,city,country,street, description)
values (40, 50, 45.267136, 19.83694,'Pharmacy BENU Mercator','Novi Sad','Serbia','Bulevar Oslobodjenja 102', 'Najpovoljniji i najpouzdaniji!'); -- id 2

-- INSERT PHARMACY ADMINS
insert into person (discriminator, address, city, country, name, surname, phone_number, pharmacy_id) values ('PharmacyAdmin', 'Jovana Jovanovica 12', 'Novi Sad', 'Srbija', 'Janko', 'Janković', '0661352720', 1);
insert into user_account (email, password, is_active, person_id, needs_password_change, pharmacy_id) values ('cpisuser+jankovic@gmail.com', '$2a$10$bJi9SxVJIp6sv2lQ1vgjxuJF9Y1SpaVpftEtOx9.K7uVkOrx91L5S', true, 6, false, 1);
-- Password for patient account is: jankovic
insert into user_authority (user_id, authority_id) values (6, 3);
insert into person (discriminator, address, city, country, name, surname, phone_number, pharmacy_id) values ('PharmacyAdmin', 'Jovana Jovanovica 12', 'Novi Sad', 'Srbija', 'Marko', 'Marković', '0661352720', 2);
insert into user_account (email, password, is_active, person_id, needs_password_change, pharmacy_id) values ('cpisuser+benu@gmail.com', '$2a$10$JyYk6irz0ZidM3Y4nVJ3ZOLNzifSWHxOxuPt8yrm/YHMqaE1UaEPe', true, 7, true, 2);
-- Password for patient account is: benumerkator
insert into user_authority (user_id, authority_id) values (7, 3);

-- INSERT ONE PATIENT
insert into person (discriminator, address, city, country, name, surname, phone_number,loyalty_points) values ('Patient', 'Marka Miljanova 7', 'Novi Sad', 'Srbija', 'Milana', 'Todorović', '0614123355',20);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+patient3@gmail.com', '$2a$10$FuaQy/6qDoxhllIMhJhRP.RKFVJSK1i3eolSAIDz5/WOV0wNh6u6K', true, 8, false);
-- Password for patient account is: milana
insert into user_authority (user_id, authority_id) values (8, 1);

-- INSERT PROMOTIONS
insert into promotion (pharmacy_id, start_date, end_date, title, content) values (1, '2020-12-25', '2021-01-14', 'Praznično sniženje!', 'Sniženje 50% na svaki drugi kupljeni proizvod! Srećni praznici!');

-- ISERT SUBSCRIBERS
insert into subscriptions (pharmacy_id, patient_id) values (1, 8);
insert into subscriptions (pharmacy_id, patient_id) values (1, 2);
--insert into subscriptions (pharmacy_id, patient_id) values (2, 4);

-- INSERT PHARMACY RATINGS
insert into pharmacy_rating(rating, patient_id,pharmacy_id) values (5,2,1);
insert into pharmacy_rating(rating, patient_id,pharmacy_id) values (3,2,2);
insert into pharmacy_rating(rating, patient_id,pharmacy_id) values (4,4,2);


insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-03 10:23','2021-02-03 11:00',3,2,1);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-04 11:23','2021-02-04 13:20',3,2,1);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-05 13:23','2021-02-05 14:00',5,4,1);

-- INSERT DRUGS
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('brufen', 5, 'Brufen',1,1);
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('aspirin', 3, 'Aspirin',1,1);
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('amfetamin', 6, 'Amfetamin',3,2);
insert into drug(code, loyalty_points, name, drug_class_id, drug_form_id) values ('kreatin', 7, 'Kreatin',2,3);

--INSERT DRUG RATING
insert into drug_rating(rating,drug_code,patient_id) values (5,'brufen',1);
insert into drug_rating(rating,drug_code,patient_id) values (4,'aspirin',1);
insert into drug_rating(rating,drug_code,patient_id) values (3,'amfetamin',2);
insert into drug_rating(rating,drug_code,patient_id) values (2,'kreatin',2);

--INSERT DRUG SPECIFICATION
insert into drug_specification(additional_notes, contraindications, manufacturer, prescription_required, recommended_daily_dose, drug_code)
values('Easy and lovely', 'None', 'Cacak Frikom', false, 3, 'brufen');
insert into drug_specification(additional_notes, contraindications, manufacturer, prescription_required, recommended_daily_dose, drug_code)
values('Fast one', 'None', 'McDonalds', false, 2, 'aspirin');
insert into drug_specification(additional_notes, contraindications, manufacturer, prescription_required, recommended_daily_dose, drug_code)
values('Very fast one', 'None', 'Liman 3', false, 1, 'amfetamin');
insert into drug_specification(additional_notes, contraindications, manufacturer, prescription_required, recommended_daily_dose, drug_code)
values('Slow one', 'None', 'Zenica KP Dom', false, 5, 'kreatin');

--INSERT AVAILABLE DRUG
insert into available_drug(available_amount, default_price, drug_code, pharmacy_id, version)
values(10, 10, 'brufen', 1, 0);
insert into available_drug(available_amount, default_price, drug_code, pharmacy_id, version)
values(15, 8, 'brufen', 2, 0);
insert into available_drug(available_amount, default_price, drug_code, pharmacy_id, version)
values(100, 15, 'aspirin', 1, 0);
insert into available_drug(available_amount, default_price, drug_code, pharmacy_id, version)
values(2, 5, 'aspirin', 2, 0);
insert into available_drug(available_amount, default_price, drug_code, pharmacy_id, version)
values(100, 15, 'kreatin', 1, 0);
insert into available_drug(available_amount, default_price, drug_code, pharmacy_id, version)
values(2, 5, 'kreatin', 2, 0);

-- INSERT ONE DERMATOLOGIST
insert into person (discriminator, address, city, country, name, surname, phone_number, type) values ('Consultant', 'Svetislava Pesica 20', 'Novi Sad', 'Srbija', 'Miroslav', 'Jovanovic', '0635212365', 1);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+dermatologist@gmail.com', '$2a$10$ijW6YiSn3zggTw5ybFOkiO.TeAxiHo/E0Ala4JPJvdza3TSI2H7ue', true, 9, false);
-- Password for dermatologist account is: dermatologist
insert into user_authority (user_id, authority_id) values (9, 5);

insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (20,1,'2021-02-03 10:23','2021-02-03 11:00',9,2,1);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (25,1,'2021-02-04 11:23','2021-02-04 13:20',9,2,1);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (30,1,'2021-02-05 13:23','2021-02-05 14:00',9,4,2);
insert into consultation (price,status,start_date,end_date,consultant_id,patient_id,pharmacy_id) values (30,1,'2021-03-05 13:23','2021-03-05 14:00',9,2,2);

-- insert into consultation_report(anamnesis,diagnosis, consultation_id) values ('Omgyyyy very sick man', 'Its fine', 1);
-- insert into consultation_report(anamnesis,diagnosis, consultation_id) values ('Omgyyyy very sick man', 'Its fine', 2);
-- insert into consultation_report(anamnesis,diagnosis, consultation_id) values ('Omgyyyy very sick man', 'Its fine', 3);
-- insert into consultation_report(anamnesis,diagnosis, consultation_id) values ('Omgyyyy very sick man', 'Its fine', 4);
-- insert into consultation_report(anamnesis,diagnosis, consultation_id) values ('Omgyyyy very sick man', 'Its fine', 5);
-- insert into consultation_report(anamnesis,diagnosis, consultation_id) values ('Omgyyyy very sick man', 'Its fine', 6);

-- INSERT DERMATOLOGIST WORKING TIME
insert into working_times(consultant_id,pharmacy_id, monday_start, monday_end, tuesday_start, tuesday_end, wednesday_start,wednesday_end,thursday_start,thursday_end,friday_start,friday_end) values (9,1, '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 17:00');
insert into working_times(consultant_id,pharmacy_id, monday_start, monday_end, saturday_start, saturday_end, sunday_start,sunday_end) values (9,2, '2021-02-03 19:00', '2021-02-03 21:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00');


-- INSERT SUPPLIER
insert into person (discriminator, address, city, country, name, surname, phone_number) values ('Supplier', 'Jovana Jovanovica 13', 'Novi Sad', 'Srbija', 'Jovica', 'Dobrica', '06323462520');
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+jovica@gmail.com', '$2a$10$hsgWgd6gOn8Kv.8M3/QImu8e8HM6S8Sj.wfcj6/5vz6lx5i21EwIS', true, 10, false);
-- Password for patient account is: jovica
insert into user_authority (user_id, authority_id) values (10, 6);

-- INSERT DRUG ORDER
insert into drug_order(deadline, status,timestamp, administrator_id, pharmacy_id) values('2021-01-01 12:00', 1 ,'2020-02-07 12:00', 6,1);
insert into drug_order(deadline, status,timestamp, administrator_id, pharmacy_id) values('2021-04-05 12:00', 0 ,'2021-02-07 12:00', 7,2);

--INSERT ORDERED DRUG
insert into ordered_drug(amount, drug_code, order_id) values (100, 'brufen', 1);
insert into ordered_drug(amount, drug_code, order_id) values (50, 'aspirin', 1);
insert into ordered_drug(amount, drug_code, order_id) values (50, 'aspirin', 2);

--INSERT OFFER (Suppliers' offers)
insert into offer(price, shipment_date, status, order_id, supplier_id) values (15, '2021-02-03 12:00', 0, 1, 10);


--INSERT RESERVATION FOR DRUG
insert into reservation(amount,date_of_creation,deadline,is_picked_up,drug_code,patient_id,pharmacy_id)
 values (15,'2021-02-08','2021-02-10',false,'brufen',4,1);


--INSERT RESERVATION (Drug reservation)
insert into reservation(amount,date_of_creation,deadline,is_picked_up,drug_code,patient_id,pharmacy_id) values (4,'2021-02-05 12:00','2021-02-09 12:00',false,'brufen',2,1);
insert into reservation(amount,date_of_creation,deadline,is_picked_up,drug_code,patient_id,pharmacy_id) values (3,'2021-03-06 12:00','2021-02-09 04:00',false,'brufen',4,2);
insert into reservation(amount,date_of_creation,deadline,is_picked_up,drug_code,patient_id,pharmacy_id) values (6,'2021-03-06 12:00','2021-02-08 12:00',false,'brufen',2,1);
insert into reservation(amount,date_of_creation,deadline,is_picked_up,drug_code,patient_id,pharmacy_id) values (5,'2021-03-06 12:00','2021-02-10 12:00',false,'brufen',4,2);
insert into reservation(amount,date_of_creation,deadline,is_picked_up,drug_code,patient_id,pharmacy_id) values (4,'2021-03-04 12:00','2021-02-09 12:00',false,'brufen',2,2);

--INSERT COMPLAINT ( only patient who had a consultation can make a complaint about consultant)
insert into complaint(content, creation_timestamp, response, consultant_id, creator_id)
values('Very sad after consultation','2021-02-02 12:00', 'Waiting on reply', 9,2);
insert into complaint(content, creation_timestamp, response, consultant_id, creator_id)
values('He is psyho, i need only pediatar','2021-04-04 12:00', 'Waiting on reply', 9,2);
insert into complaint(content, creation_timestamp, response,  creator_id, pharmacy_id)
values('Very expensive one','2021-05-05 12:00', 'Waiting on reply', 2,1);
insert into complaint(content, creation_timestamp, response, consultant_id, creator_id)
values('Omg, there is no need for this type of conversation...','2021-03-03 12:00', 'Waiting on reply', 9,4);

-- INSERT VACATION REQUESTS
insert into vacation_request (consultant_id, status, start_date, end_date) values (5, 0, '2021-03-25', '2021-03-29');
insert into vacation_request (consultant_id, status, start_date, end_date, response) values (5, 2, '2021-01-25', '2021-01-29', 'Ne možemo da prihvatimo odmor u tom periodu zato što je zahtjev prekasno poslat.');

insert into vacation_request (consultant_id, status, start_date, end_date) values (9, 0, '2021-05-25', '2021-05-29');

insert into working_times(consultant_id,pharmacy_id, monday_start, monday_end, tuesday_start, tuesday_end, wednesday_start,wednesday_end,thursday_start,thursday_end,friday_start,friday_end,saturday_start,saturday_end, sunday_start, sunday_end) values (5,1, '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00');
insert into working_times(consultant_id,pharmacy_id, monday_start, monday_end, tuesday_start, tuesday_end, wednesday_start,wednesday_end,thursday_start,thursday_end,friday_start,friday_end,saturday_start,saturday_end, sunday_start, sunday_end) values (3,2, '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:00');

-- ISERT DERMATOLOGISTS WHO WORK IN ONLY ONE PHARMACY
insert into person (discriminator, address, city, country, name, surname, phone_number, type) values ('Consultant', 'Svetislava Pesica 20', 'Novi Sad', 'Srbija', 'Stanko', 'Marković', '0635212365', 1);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+dermatologist2@gmail.com', '$2a$10$ijW6YiSn3zggTw5ybFOkiO.TeAxiHo/E0Ala4JPJvdza3TSI2H7ue', true, 11, false);
-- Password for dermatologist account is: dermatologist
insert into user_authority (user_id, authority_id) values (11, 5);

insert into working_times(consultant_id,pharmacy_id, monday_start, monday_end, saturday_start, saturday_end) values (11,2, '2021-02-03 19:00', '2021-02-03 21:00','2021-02-03 10:00', '2021-02-03 18:00');


insert into person (discriminator, address, city, country, name, surname, phone_number, type) values ('Consultant', 'Svetislava Pesica 20', 'Novi Sad', 'Srbija', 'Stanka', 'Janković', '0635212365', 1);
insert into user_account (email, password, is_active, person_id, needs_password_change) values ('cpisuser+dermatologist3@gmail.com', '$2a$10$ijW6YiSn3zggTw5ybFOkiO.TeAxiHo/E0Ala4JPJvdza3TSI2H7ue', true, 12, false);
-- Password for dermatologist account is: dermatologist
insert into user_authority (user_id, authority_id) values (12, 5);

insert into working_times(consultant_id,pharmacy_id, monday_start, monday_end, saturday_start, saturday_end, sunday_start,sunday_end) values (12,2, '2021-02-03 12:00', '2021-02-03 20:00','2021-02-03 10:00', '2021-02-03 18:00', '2021-02-03 10:00', '2021-02-03 18:30');

-- INSERT DRUG REQUESTS
insert into drug_request (consultant_id, pharmacy_id, drug_code, timestamp) values (9, 1, 'aspirin', '2020-10-14');
insert into drug_request (consultant_id, pharmacy_id, drug_code, timestamp) values (9, 1, 'brufen', '2020-12-14');

insert into allergies (patient_id, drug_code) values (2,'brufen');
insert into allergies (patient_id, drug_code) values (2,'aspirin');

insert into alternate_drugs(drug_code, alternate_drug_code) values('amfetamin','brufen');