INSERT INTO roles (name)
SELECT 'ROLE_SUPERADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_DENTIST');

INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_PATIENT');

INSERT INTO users (email, name, surname, role_id, telephone_number)
VALUES (
           'andrijinkristina@gmail.com',
           'Kristina',
           'Andrijin',
           (SELECT id FROM roles WHERE name = 'ROLE_DENTIST'),
            '+381605526395'
       );
