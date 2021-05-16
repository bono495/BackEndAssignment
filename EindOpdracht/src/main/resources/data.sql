-- Clients

insert into clients (first_name, last_name, address, email, phonenumber, created_at, updated_at)
values
('Peter', 'Gilles', 'Ergens in brabant', 'peter@foxwild.com', '0612345678', CURRENT_DATE, CURRENT_DATE),
('Frank', 'Lammers', 'Bij de jumbo', 'frank@undercover.com', '0612345679', CURRENT_DATE, CURRENT_DATE);

-- Cars

insert into cars (brand, papers, year, client_id, created_at, updated_at)
values
('Rolls Royce', '', 2020, 1, CURRENT_DATE, CURRENT_DATE),
('BMW', '', 2015, 2, CURRENT_DATE, CURRENT_DATE);


-- Users

insert into users (username, email, password, created_at, updated_at)
values
('engineer', 'engineer@bmw.nl', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', CURRENT_DATE, CURRENT_DATE),
('administration', 'administrer@bmw.nl', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', CURRENT_DATE, CURRENT_DATE),
('cashier', 'administrer@bmw.nl', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', CURRENT_DATE, CURRENT_DATE),
('backoffice', 'backoffice@bmw.nl', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', CURRENT_DATE, CURRENT_DATE),
('admin', 'admin@bmw.nl', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', CURRENT_DATE, CURRENT_DATE);

insert into roles (name, created_at, updated_at)
values
('Engineer', CURRENT_DATE, CURRENT_DATE),
('Administration', CURRENT_DATE, CURRENT_DATE),
('Cashier', CURRENT_DATE, CURRENT_DATE),
('Admin', CURRENT_DATE, CURRENT_DATE);

insert into user_role (user_id, role_id)
values
(5,1),
(5,2),
(5,3),
(5,4),
(4,4),
(3,3),
(2,2),
(1,1);

-- Resources

insert into resources (name, price, stock, created_at, updated_at)
values
('Bougie', 20, 30, CURRENT_DATE, CURRENT_DATE),
('Brake', 50, 10, CURRENT_DATE, CURRENT_DATE),
('Poke', 10, 50, CURRENT_DATE, CURRENT_DATE),
('Remschijf', 10, 50, CURRENT_DATE, CURRENT_DATE),
('Gearbox', 200, 2, CURRENT_DATE, CURRENT_DATE);

-- Actions

insert into actions (name, duration, price, created_at, updated_at)
values
('Inspection', 60, 45, CURRENT_DATE, CURRENT_DATE),
('Remschijf vervangen', 120, 100, CURRENT_DATE, CURRENT_DATE);

insert into action_resource (action_id, resource_id)
values
(2,4);

-- Repairs

insert into repairs (payed, status, car_id, created_at, updated_at)
values
(false, 'Inspection', 1, CURRENT_DATE, CURRENT_DATE);

insert into repair_action (repair_id, action_id)
values
(1, 1);