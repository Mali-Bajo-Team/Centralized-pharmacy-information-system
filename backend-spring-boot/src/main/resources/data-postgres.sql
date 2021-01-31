insert into person (discriminator, address, city, country, name, surname, phone_number) values ('SystemAdmin', 'Marka Miljanova 7', 'Novi Sad', 'Srbija', 'Milana', 'Todorović', '0601452700');

insert into user_account (email, password, is_active, person_id) values ('admin@gmail.com', 'admin', true, 1);

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
