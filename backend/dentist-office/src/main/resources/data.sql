INSERT INTO roles (name)
SELECT 'ROLE_DENTIST'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_DENTIST');

INSERT INTO roles (name)
SELECT 'ROLE_PATIENT'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_PATIENT');

INSERT INTO users (email, role_id)
VALUES (
           'andrijinkristina@gmail.com',
           (SELECT id FROM roles WHERE name = 'ROLE_DENTIST')
       );
