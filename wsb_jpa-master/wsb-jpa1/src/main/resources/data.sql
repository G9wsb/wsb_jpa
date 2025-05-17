-- adresy
insert into address (id, city, address_line1, address_line2, postal_code) values
(901, 'xx', 'yy', 'city', '60-400'),
(902, 'Wroclaw', 'Ul. Grunwaldzka 1', null, '00-001'),
(903, 'Boleslawiec', 'Ul. Gliniana 2', 'Mieszkanie 5', '00-002'),
(904, 'Lodz', 'Ul. Podwodna 3', null, '00-003'),
(905, 'Poznan', 'Ul. Rogalikowa 4', null, '00-004');


-- lekarze
insert into doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) values
(1, 'Jacek', 'Placek', '123456789', 'jacek.placek@example.com', 'DOC001', 'GP', 901),
(2, 'Gryzelda', 'Bąk', '987654321', 'gryzelda.bak@example.com', 'DOC002', 'DERMATOLOGIST', 902);

-- pacjenci
insert into patient ( first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id, bmi, version) values
( 'Marek', 'Skwarek', '90051211122', 'marek.skwarek@example.com', 'PAT001', '1990-05-12', 903, 20, 0),
( 'Monika', 'Kwiat', '85092322211', 'monika.kwiat@example.com', 'PAT002', '1985-09-23', 904, 27, 0);
-- wizyty
insert into visit (description, time, doctor_id, patient_id) values
('Kontrola', '2025-06-01T10:00:00', 1, 1),
('Badanie skóry', '2025-07-15T14:00:00', 2, 2),
('Szczepienie', '2025-01-10T09:00:00', 1, 1),
('Szczepienie 2 dawka', '2025-02-10T09:00:00', 1, 1),
('Szczepienie 3 dawka', '2025-03-10T09:00:00', 1, 1),
('Konsultacja', '2025-01-20T11:30:00', 2, 2);

-- leczenia
insert into medical_treatment (id, description, type, visit_id) values 
(1, 'Usg brzucha', 'USG', 1),
(2, 'Ekg serca', 'EKG', 2),
(3, 'Rtg klatki', 'RTG', 2),
(4, 'Ekg kontrolne', 'EKG', 4);
