INSERT INTO "app_user" (login, password, role) VALUES
('admin', '$2a$10$Wl07HVD0ejHo5LCWOQ5qz..Qt00UgKDYbpTLWnxBf0Q4ahS3l1EUK', 'ADMIN'), -- admin
('user', '$2a$10$B8o4gd8zK1yoKRKrDwO3WOhyCZUcojZpZgRM6mo5le2DjC9QQAKUK', 'USER'); -- user

INSERT INTO "region" (name) VALUES
('Київська'),
('Львівська'),
('Одеська'),
('Харківська');

INSERT INTO "district_city" (name, region_id) VALUES
('Шевченківський', (SELECT id FROM region WHERE name = 'Київська')),
('Галицький', (SELECT id FROM region WHERE name = 'Львівська')),
('Приморський', (SELECT id FROM region WHERE name = 'Одеська')),
('Слобідський', (SELECT id FROM region WHERE name = 'Харківська'));

INSERT INTO "ecological_problem" (description, district_city_id) VALUES
('Забруднення річок', (SELECT id FROM district_city WHERE name = 'Шевченківський')),
('Викиди промислових підприємств', (SELECT id FROM district_city WHERE name = 'Галицький')),
('Забруднення морської води', (SELECT id FROM district_city WHERE name = 'Приморський')),
('Перевищення рівня шуму', (SELECT id FROM district_city WHERE name = 'Слобідський'));
